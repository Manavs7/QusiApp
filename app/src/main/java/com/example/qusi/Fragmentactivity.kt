package com.example.qusi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class Fragmentactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragmentactivity)

        //nav controller aanmaken
        val navController = findNavController(R.id.container_fragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navgation_view)
        //var doorgeven aan setup
        bottomNavigationView.setupWithNavController(navController)


    }

}
