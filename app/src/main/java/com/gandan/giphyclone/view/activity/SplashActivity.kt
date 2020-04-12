package com.gandan.giphyclone.view.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.gandan.giphyclone.R

class SplashActivity : AppCompatActivity() {

    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        checkConnection()
    }

    /** Connection check **/

    fun checkConnection(){
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true

        countDownTimer = object : CountDownTimer(1000, 100) {
            override fun onFinish() {
                moveToMain()
            }

            override fun onTick(p0: Long) {
                if(!isConnected) {
                    cancel()
                    showConnectionToast()
                }
            }

        }.start()
    }

    fun moveToMain(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun showConnectionToast(){
        Toast.makeText(this, resources.getString(R.string.checkConnection), Toast.LENGTH_SHORT).show()
        onBackPressed()
    }
}
