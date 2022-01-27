package com.example.qusi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Userdata : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdata)

        //Ga naar Home fragmet(Fragment activity)
        GoToHome()
    }


    fun GoToHome()
    {
        val btnUserdata = findViewById<Button>(R.id.Userdatabutton)
        btnUserdata.setOnClickListener {
            val intent = Intent(this,Fragmentactivity::class.java)
            startActivity(intent)
        }
    }
}