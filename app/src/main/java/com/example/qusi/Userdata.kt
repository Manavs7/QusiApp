package com.example.qusi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Userdata : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdata)

       //Ga naar UserActivity
        GoToUserActivity()
    }


    fun GoToUserActivity()
    {
        val btnUserdata = findViewById<Button>(R.id.Userdatabutton)
        btnUserdata.setOnClickListener {
            val intent = Intent(this,UserActivity::class.java)
            startActivity(intent)
        }
    }
}