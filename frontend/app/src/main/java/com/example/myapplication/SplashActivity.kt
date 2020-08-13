package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.api.ApiAdapter

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ApiAdapter.setContext(applicationContext)

        /*applicationContext.getSharedPreferences(
            "CONCIERGE_APP",
            Context.MODE_PRIVATE
        ).edit().clear().apply()*/

        val authToken = applicationContext.getSharedPreferences(
            "CONCIERGE_APP",
            Context.MODE_PRIVATE
        ).getString("AUTH_TOKEN", null)

        if (authToken != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            applicationContext.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            finish()
        } else {
            val intent = Intent(applicationContext, Login::class.java)
            applicationContext.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            finish()
        }
    }
}