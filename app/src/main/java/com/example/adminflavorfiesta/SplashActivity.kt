package com.example.adminflavorfiesta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // Using a Handler to post a delayed action
        Handler(Looper.getMainLooper()).postDelayed({
            // Creating an Intent to navigate to the LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) // Start the LoginActivity
            finish() // Finish the current SplashActivity to prevent the user from going back to it
        }, 1600) // Delay for 1600 milliseconds (1.6 seconds)
    }
}