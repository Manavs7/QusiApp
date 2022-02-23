package com.example.qusi


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.textfield.TextInputLayout


class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        //Ga naar Home fragmet(Fragment activity)
        GoToHome()

    }

    fun GoToHome()
    {
        val btnReadybutton = findViewById<Button>(R.id.readybutton)
        btnReadybutton.setOnClickListener {
            val intent = Intent(this,Fragmentactivity::class.java)
            startActivity(intent)
        }
    }
}