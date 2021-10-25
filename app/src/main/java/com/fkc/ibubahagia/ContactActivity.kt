package com.fkc.ibubahagia

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fkc.ibubahagia.databinding.ActivityContactBinding
import com.google.gson.Gson
import java.io.IOException
import java.nio.charset.Charset

class ContactActivity : AppCompatActivity(), RecyclerViewClickListener {
    private lateinit var binding : ActivityContactBinding
    private lateinit var mHomeWatcher : HomeWatcher
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact)

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

        val recyclerView = binding.contactsList
        val contactAdapter = ContactListAdapter(contactsData)
        contactAdapter.listener = this
        recyclerView.apply {
            this.adapter = contactAdapter
            this.layoutManager = LinearLayoutManager(this@ContactActivity)
        }

        val btnHome = binding.btnHome
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val link1 = binding.subTitle1
        link1.movementMethod = LinkMovementMethod.getInstance()
        link1.setLinkTextColor(Color.parseColor("#C32969"))
    }

    override fun onItemClicked(view: View, contact: Contact) {
        val lokasi = contact.plusKode
        var namaLokasi = contact.namaKlinik
        namaLokasi = namaLokasi!!.replace(" ", "+")
        val gmmIntentUri = Uri.parse("geo:$lokasi?q=$namaLokasi")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        }
        else {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://play.google.com/store/apps/details?id=com.google.android.apps.maps")
                setPackage("com.android.vending")
            }
            startActivity(intent)
        }
    }

    /* Convert JSON String to BaseWatch Model via GSON */
    private val contactsData: List<Contact>
        get() {
            val jsonString =
                getAssetsJSON(JSON_FILE_ANDROID_WEAR)
//            Log.d(TAG, "Json: $jsonString")
            val gson = Gson()
            val baseContact =
                gson.fromJson(jsonString, ContactList::class.java)
            return baseContact.contact
        }

    /* Get File in Assets Folder */
    private fun getAssetsJSON(fileName: String?): String? {
        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val inputStream = this.assets.open(fileName!!)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }

    companion object {
        private const val JSON_FILE_ANDROID_WEAR = "kontak.json"
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