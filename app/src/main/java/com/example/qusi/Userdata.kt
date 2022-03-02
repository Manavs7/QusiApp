package com.example.qusi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.facebook.stetho.Stetho

class Userdata : AppCompatActivity() {
    //Hoofdvariabele

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdata)
        //Variabels
        val btnUserdata = findViewById<Button>(R.id.Userdatabutton)
        val username = findViewById<EditText>(R.id.etusername)
        val age = findViewById<EditText>(R.id.etage)
        val height = findViewById<EditText>(R.id.etheight)
        val weight = findViewById<EditText>(R.id.etweight)
        //stetho
        //Stetho.initializeWithDefaults(this)
        //Ga naar UserActivity
        btnUserdata.setOnClickListener {

            if(username.text.toString().length >0 ||
                    age.text.toString().length > 0 ||
                        height.text.toString().length > 0 ||
                          weight.text.toString().length> 0
                    )
            {
                User.Username = username.text.toString()
                User.Age = age.text.toString().toInt()
                User.Height = height.text.toString().toInt()
                User.Weight = weight.text.toString().toInt()

            }

            else
            {
                Toast.makeText(this,"Please FILL ALL Data's",Toast.LENGTH_LONG)
            }

            val intent = Intent(this,UserActivity::class.java)
            startActivity(intent)

        }


    }

}