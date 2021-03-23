package com.fkc.ibubahagia

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import android.text.method.LinkMovementMethod
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import com.fkc.ibubahagia.databinding.ActivityMainBinding
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sharedPreference : SharedPreference = SharedPreference(this)
    private lateinit var mHomeWatcher : HomeWatcher
    private var musicState = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        musicState = sharedPreference.getValueBoolean("TOGGLE_MUSIC", true)

        if (musicState) {
            doBindService()
            val music = Intent()
            music.setClass(this, MusicService::class.java)
            startService(music)
        }

        mHomeWatcher = HomeWatcher(this)
        mHomeWatcher.setOnHomePressedListener(object : HomeWatcher.OnHomePressedListener {
            override fun onHomePressed() {
                if (mServ != null) {
                    mServ!!.pauseMusic()
                }
            }

            override fun onHomeLongPressed() {
                if (mServ != null) {
                    mServ!!.pauseMusic()
                }
            }
        })
        mHomeWatcher.startWatch()

        val buttonIntro = binding.itemTitle0
        buttonIntro.setOnClickListener {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }
        val buttonKK = binding.itemTitle1
        buttonKK.setOnClickListener {
            val intent = Intent(this, KebkehActivity::class.java)
            startActivity(intent)
        }
        val buttonAid = binding.itemTitle2
        buttonAid.setOnClickListener {
            val intent = Intent(this, AidActivity::class.java)
            startActivity(intent)
        }
        val buttonTmd = binding.itemTitle3
        buttonTmd.setOnClickListener {
            val intent = Intent(this, TmdActivity::class.java)
            startActivity(intent)
        }
        val buttonCheck = binding.itemTitle4
        buttonCheck.setOnClickListener {
            val intent = Intent(this, CheckconActivity::class.java)
            startActivity(intent)
        }
        val buttonHelp = binding.itemTitle5
        buttonHelp.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }
        val buttonTentang = binding.aboutButton
        buttonTentang.setOnClickListener {
            showCustomAlert()
        }
        val toggleMusic = binding.musicToggle
        toggleMusic.isChecked = musicState
        toggleMusic.setOnClickListener {
            if (toggleMusic.isChecked) {
                doBindService()
                val music = Intent()
                music.setClass(this, MusicService::class.java)
                startService(music)
                musicState = true
                if (mServ != null) {
                    mServ!!.startMusic()
                }
            } else {
                musicState = false
                if (mServ != null) {
                    mServ!!.stopMusic()
                }
            }
            sharedPreference.save("TOGGLE_MUSIC", musicState)
        }
    }
    private fun showCustomAlert() {
        val dialogView = layoutInflater.inflate(R.layout.about_dialog, null) as ConstraintLayout
        val customDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .create()
        val decorView = customDialog.window!!.decorView
        decorView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                decorView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                // Find out how much of the scrolling view is usable by its child.
                val scrollingView: NestedScrollView = decorView.findViewById(R.id.isian)
                val scrollingViewPadding = scrollingView.paddingTop + scrollingView.paddingBottom
                val scrollingUsableHeight = scrollingView.height - scrollingViewPadding
                // If the child view fits in the scrolling view, then we are done.
                val childView = scrollingView.getChildAt(0)
                if (childView.height <= scrollingUsableHeight) {
                    return
                }
                // Child doesn't currently fit in the scrolling view. Resize the top-level
                // view so the child either fits or is forced to scroll because the maximum
                // height is reached. First, find out how much space is allowed by the decor view.
                val displayRectangle = Rect()
                decorView.getWindowVisibleDisplayFrame(displayRectangle)
                val decorViewPadding = decorView.paddingTop + decorView.paddingBottom
                val decorUsableHeight = displayRectangle.height() - decorViewPadding - scrollingViewPadding

                // Compute the height of the dialog that will 100% fit the scrolling content and
                // reduce it if it won't fit in the maximum allowed space.
                val heightToFit = dialogView.height + childView.height - scrollingUsableHeight
                dialogView.minHeight = min(decorUsableHeight, heightToFit)
            }
        })
        val btDismiss = dialogView.findViewById<Button>(R.id.button_ok)
        btDismiss.setOnClickListener {
            customDialog.dismiss()
        }
        val link1 = dialogView.findViewById<TextView>(R.id.text_about_2)
        val link2 = dialogView.findViewById<TextView>(R.id.text_about_4)
        val link3 = dialogView.findViewById<TextView>(R.id.text_about_5)
        val link4 = dialogView.findViewById<TextView>(R.id.another_link1)
        val link5 = dialogView.findViewById<TextView>(R.id.another_link2)
        link1.movementMethod = LinkMovementMethod.getInstance()
        link2.movementMethod = LinkMovementMethod.getInstance()
        link3.movementMethod = LinkMovementMethod.getInstance()
        link4.movementMethod = LinkMovementMethod.getInstance()
        link5.movementMethod = LinkMovementMethod.getInstance()
        link1.setLinkTextColor(Color.parseColor("#C32969"))
        link2.setLinkTextColor(Color.parseColor("#C32969"))
        link3.setLinkTextColor(Color.parseColor("#C32969"))
        link4.setLinkTextColor(Color.parseColor("#C32969"))
        link5.setLinkTextColor(Color.parseColor("#C32969"))
        customDialog.show()
    }
    override fun onBackPressed() {
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage("Apakah anda ingin keluar dari aplikasi?")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Ya") { _, _ -> finish()
            }
            // negative button text and action
            .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel()
            }

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("KONFIRMASI KELUAR")
        // show alert dialog
        alert.show()
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
        val isScreenOn: Boolean = pm.isInteractive
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