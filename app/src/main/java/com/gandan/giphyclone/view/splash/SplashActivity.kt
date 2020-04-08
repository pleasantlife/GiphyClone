package com.gandan.giphyclone.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.gandan.giphyclone.R
import com.gandan.giphyclone.view.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        countDownTimer = object : CountDownTimer(1000, 100) {
            override fun onFinish() {
                startActivity(intent)
            }

            override fun onTick(p0: Long) {
            }

        }.start()

    }
}
