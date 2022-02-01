package com.example.qusi

import android.content.Intent
import android.icu.number.Scale
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

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



      Handler(Looper.myLooper()!!).postDelayed({
          val intent = Intent(this, Userdata::class.java)
          startActivity(intent)
          finish()
        },4000)





    }

}

