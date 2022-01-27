package com.example.qusi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Ga naar Userdata activity
        GotoUserdata()

    }
    //function login
    fun GotoUserdata()
    {
        val btnLogin = findViewById<Button>(R.id.homebutton)
        btnLogin.setOnClickListener {
            val intent = Intent(this,Userdata::class.java)
            startActivity(intent)
        }
    }
}

