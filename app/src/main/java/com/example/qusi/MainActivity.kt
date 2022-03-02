package com.example.qusi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.facebook.stetho.Stetho

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logoImageview = findViewById<ImageView>(R.id.logo)
        val txtView = findViewById<TextView>(R.id.txtQusi)
        //Animatie inladen
        val animationLogo = AnimationUtils.loadAnimation(this,R.anim.animation_logo)
        val animationText = AnimationUtils.loadAnimation(this,R.anim.animation_text)

        logoImageview.startAnimation(animationLogo)
        txtView.startAnimation(animationText)
        //Stetho
        Stetho.initializeWithDefaults(this)

      Handler(Looper.myLooper()!!).postDelayed({

          //if number of table rows in the table USERS < 1 --> go to userdata activity | else -> Home

          //database variables
           val helper = SQLiteHelper(this)
          val db = helper.readableDatabase
          val rs = db.rawQuery("SELECT * FROM USERS",null)


           if(rs.count < 1)
           {
               val intent = Intent(this, Userdata::class.java)
               startActivity(intent)
           }

          else
           {
               val intent = Intent(this, Fragmentactivity::class.java)
               startActivity(intent)
           }

          finish()
        },4000)





    }

}

