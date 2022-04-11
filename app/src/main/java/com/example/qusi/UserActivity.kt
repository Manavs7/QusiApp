package com.example.qusi


import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlin.math.round


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

            //Database
            val helper = SQLiteHelper(this)
            val db = helper.writableDatabase
            var cv = ContentValues()

            //Add all userdata to database
             helper.InsertData()

           //add usergoal data to database

            cv.put("MaintenanceCal", User.uMainetenanceCal)
            cv.put("ProteinCal", User.uProteinCal)
            cv.put("ProteinG", User.uProteinG)
            cv.put("FatCal", User.uFatCal)
            cv.put("FatG", User.uFatG)
            cv.put("CarbsCal", User.uCarbsCal)
            cv.put("CarbsG", User.uCarbsG)

            var result = db.insert("GOAL", null, cv)

            if(result == (-1).toLong())
            {
                Toast.makeText(applicationContext,"Failed", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(applicationContext,"GOAL Setup Ready", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this, Fragmentactivity::class.java)
            startActivity(intent)


        }

    }

}