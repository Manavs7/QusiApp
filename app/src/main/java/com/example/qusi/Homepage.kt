package com.example.qusi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        //Get data from intent and set it into a variable
        val username = intent.getStringExtra("Name")
        val age = intent.getStringExtra("Age")
        val weight = intent.getStringExtra("Weight")
        val height = intent.getStringExtra("Height")
        //Textview
        val tvData = findViewById<TextView>(R.id.tvData)
        //set Text
        tvData.text = "Username: $username \n Age: $age years \n Weight: $weight kg\n Height: $height cm"
    }
}