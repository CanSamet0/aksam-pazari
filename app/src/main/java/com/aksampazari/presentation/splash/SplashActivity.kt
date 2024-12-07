package com.aksampazari.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aksampazari.R
import com.aksampazari.presentation.MainActivity
import com.aksampazari.presentation.login.LoginAndRegisterActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Log.i("INFO", "Created Splash Screen")
        Handler().postDelayed({
            startActivity(Intent(this, LoginAndRegisterActivity::class.java))
            finish()
        }, splashTimeOut)
    }
}