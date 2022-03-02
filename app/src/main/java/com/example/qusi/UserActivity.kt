package com.example.qusi


import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*


class UserActivity : AppCompatActivity() {

     private lateinit var sprActivites: Spinner
     private lateinit var radiobtnMale: RadioButton
     private lateinit var radiobtnFemale: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        //spinner
        sprActivites = findViewById(R.id.sp_activty) as Spinner

        val arrActivities = arrayOf("Little or no exercise", "Moderate 1-3 times/week", "Heavy 4-5 times/week", "Daily/Intense 6-7 times/week")
        sprActivites.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrActivities)



        radiobtnMale = findViewById(R.id.rdoMale)
        radiobtnFemale = findViewById(R.id.rdoFemale)


        //Ga naar Home fragmet(Fragment activity)
        val btnReadybutton = findViewById<Button>(R.id.readybutton)
        btnReadybutton.setOnClickListener {

            //Database
            val helper = SQLiteHelper(this)
            val db = helper.writableDatabase
            var cv = ContentValues()



            //Spinner data
           User.UActivity = sprActivites.getItemAtPosition(sprActivites.selectedItemPosition).toString()

            //radiobutton data
            if(radiobtnMale.isChecked)
            {
               User.Gender = radiobtnMale.text.toString()
            }
            else if(radiobtnFemale.isChecked)
            {
                User.Gender = radiobtnFemale.text.toString()
            }
            //Add all userdata to database
             helper.InsertData()

            val intent = Intent(this, Fragmentactivity::class.java)
            startActivity(intent)


        }

    }

}