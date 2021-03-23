package com.fkc.ibubahagia

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fkc.ibubahagia.databinding.ScreenSplashBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var cdt: CountDownTimer
    private lateinit var binding: ScreenSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.screen_splash)

        val buttonMasuk = binding.buttonMasuk
        val spinner = binding.progressBar

        buttonMasuk.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            this.finish()
        }

        cdt = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
                spinner.visibility = View.VISIBLE
            }
            override fun onFinish() {
                spinner.visibility = View.INVISIBLE
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            cdt.start()
        }, 1000)
        Handler(Looper.getMainLooper()).postDelayed({
            buttonMasuk.visibility = View.VISIBLE
        }, 5000)
    }
}