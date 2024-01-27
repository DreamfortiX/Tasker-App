package com.example.project_task.Ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.project_task.R
import com.google.firebase.auth.FirebaseAuth

class splash : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if (auth.currentUser != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, signup::class.java)
                startActivity(intent)
            }
            finish()
        }, (1 * 1000).toLong())
    }
}
