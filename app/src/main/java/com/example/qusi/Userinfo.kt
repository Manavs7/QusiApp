package com.example.qusi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Userinfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinfo)

        val nextbtn = findViewById<Button>(R.id.Nextbutton)
        //on button click
        nextbtn.setOnClickListener{
               sendDatatoHome()
        }
    }

    private fun sendDatatoHome() {

        //Collect Data
        val editHeight = findViewById<EditText>(R.id.et_height)
        val editUsername = findViewById<EditText>(R.id.et_username)
        val editAge = findViewById<EditText>(R.id.et_age)
        val editWeight = findViewById<EditText>(R.id.et_weight)

        //Start new activity
        val intent = Intent(this, Homepage::class.java)
         intent.putExtra("Name", editUsername.text.toString())
         intent.putExtra("Age", editAge.text.toString())
         intent.putExtra("Height", editHeight.text.toString())
         intent.putExtra("Weight", editWeight.text.toString())

        startActivity(intent)
    }


}


