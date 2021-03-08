package com.fkc.ibubahagia

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.fkc.ibubahagia.databinding.ActivityAidPlaceholderBinding
import com.google.android.material.tabs.TabLayoutMediator

class TmdActivity : AppCompatActivity() {
    private lateinit var mAdapter: ViewPagerAdapter
    lateinit var layouts: IntArray
    private lateinit var binding: ActivityAidPlaceholderBinding
    private lateinit var mHomeWatcher : HomeWatcher
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAidPlaceholderBinding.inflate(layoutInflater)
        binding.title.text = resources.getString(R.string.menu_item_3)
        setContentView(binding.root)

        doBindService()
        val music = Intent()
        music.setClass(this, MusicService::class.java)
        startService(music)

        mHomeWatcher = HomeWatcher(this)
        mHomeWatcher.setOnHomePressedListener(object: HomeWatcher.OnHomePressedListener {
            override fun onHomePressed() {
                if (mServ != null)
                {
                    mServ!!.pauseMusic()
                }
            }
            override fun onHomeLongPressed() {
                if (mServ != null)
                {
                    mServ!!.pauseMusic()
                }
            }
        })
        mHomeWatcher.startWatch()

        init()
    }
    private fun init() {
        layouts = intArrayOf(
                R.layout.view_tmd_1,
                R.layout.view_tmd_2,
                R.layout.view_tmd_3,
                R.layout.view_tmd_4,
                R.layout.view_tmd_5,
                R.layout.view_last_page)
        mAdapter = ViewPagerAdapter()
        binding.viewPager.adapter = mAdapter
        binding.viewPager.offscreenPageLimit = 6
        TabLayoutMediator(binding.layoutDots,binding.viewPager){ _, _ ->

        }.attach()
        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback)
        binding.btnPrev.setOnClickListener {
            val current: Int = getItem(-1)
            if (current >= 0) {
                binding.viewPager.currentItem = current
            }
            else {
                this.finish()
            }
        }
        binding.btnNext.setOnClickListener {
            val current: Int = getItem(+1)
            if (current < layouts.size) {
                binding.viewPager.currentItem = current
            } else {
                this.finish()
            }
        }
    }
    fun goHome(v: View?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
    private fun getItem(i: Int): Int {
        return binding.viewPager.currentItem + i
    }
    fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
        val spannableString = SpannableString(this.text)
        var startIndexOfLink = -1
        for (link in links) {
            val clickableSpan = object : ClickableSpan() {
                override fun updateDrawState(textPaint: TextPaint) {
                    // use this to change the link color
                    textPaint.color = textPaint.linkColor
                    // toggle below value to enable/disable
                    // the underline shown below the clickable text
                    textPaint.isUnderlineText = true
                }
                override fun onClick(view: View) {
                    Selection.setSelection((view as TextView).text as Spannable, 0)
                    view.invalidate()
                    link.second.onClick(view)
                }
            }
            startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
            spannableString.setSpan(
                    clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        this.movementMethod =
                LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }
    private var pageChangeCallback: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when (position) {
                0 -> {
                    binding.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30F)
                    binding.title.gravity = Gravity.CENTER
                    binding.btnPrev.visibility = View.INVISIBLE
                    binding.btnNext.visibility = View.VISIBLE
                }
                layouts.size - 1 -> {
                    binding.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
                    binding.title.gravity = Gravity.END
                    binding.btnPrev.visibility = View.VISIBLE
                    binding.btnNext.visibility = View.INVISIBLE
                    val text = findViewById<TextView>(R.id.epilog)
                    text.text = resources.getString(R.string.konten_tmd_fourth_epilog)
                    text.makeLinks(
                        Pair("Cek keadaanmu", View.OnClickListener {
                            val intent = Intent(this@TmdActivity, CheckconActivity::class.java)
                            startActivity(intent)
                            finish()
                        })
                    )
                }
                else -> {
                    binding.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
                    binding.title.gravity = Gravity.END
                    binding.btnPrev.visibility = View.VISIBLE
                    binding.btnNext.visibility = View.VISIBLE
                }
            }
        }
    }
    inner class ViewPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(viewType, parent, false)
            return SliderViewHolder(view)
        }
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}
        override fun getItemViewType(position: Int): Int {
            return layouts[position]
        }
        override fun getItemCount(): Int {
            return layouts.size
        }
        inner class SliderViewHolder(view: View?) : RecyclerView.ViewHolder(view!!)
    }
    private var mIsBound = false
    private var mServ: MusicService? = null
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            mServ = (binder as MusicService.ServiceBinder).service
        }
        override fun onServiceDisconnected(name: ComponentName) {
            mServ = null
        }
    }

    private fun doBindService() {
        bindService(
                Intent(this, MusicService::class.java),
                serviceConnection, Context.BIND_AUTO_CREATE
        )
        mIsBound = true
    }

    private fun doUnbindService() {
        if (mIsBound) {
            unbindService(serviceConnection)
            mIsBound = false
        }
    }

    override fun onResume() {
        super.onResume()
        if (mServ != null) {
            mServ!!.resumeMusic()
        }
    }

    override fun onPause() {
        super.onPause()
        val pm =
                getSystemService(Context.POWER_SERVICE) as PowerManager
        val isScreenOn: Boolean
        isScreenOn = pm.isInteractive
        if (!isScreenOn) {
            if (mServ != null) {
                mServ!!.pauseMusic()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        doUnbindService()
        mHomeWatcher.stopWatch()
        val music = Intent()
        music.setClass(this, MusicService::class.java)
        stopService(music)
    }
}