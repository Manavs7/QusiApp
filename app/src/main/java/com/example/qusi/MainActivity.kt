package com.example.qusi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Button click
        goToLogin()
    }


    //function for Button Click-> new activity(loginpage)
    private fun goToLogin(){
        val button:Button = findViewById(R.id.homebutton)
        button.setOnClickListener {
            val intent = Intent(this,Userinfo::class.java)
            startActivity(intent)
        }
    }

}

