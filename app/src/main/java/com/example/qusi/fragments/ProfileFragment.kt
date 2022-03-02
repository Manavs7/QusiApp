package com.example.qusi.fragments

import android.app.AlertDialog
import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.qusi.R
import com.example.qusi.SQLiteHelper
import com.example.qusi.User


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_profile, container, false)
        val username = v.findViewById<EditText>(R.id.profUsername)
        val age = v.findViewById<EditText>(R.id.profAge)
        val weight = v.findViewById<EditText>(R.id.profWeight)
        val height = v.findViewById<EditText>(R.id.profHeight)
        val updatebtn = v.findViewById<Button>(R.id.btnUpdate)

        //spinnder
        val sprUpdateActivites = v.findViewById<Spinner>(R.id.sp_Updateactivty)
        val arrUpdateActivities = arrayOf("Little or no exercise", "Moderate 1-3 times/week", "Heavy 4-5 times/week", "Daily/Intense 6-7 times/week")
        sprUpdateActivites.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1, arrUpdateActivities)

        //database
        val helper = SQLiteHelper(requireContext())
        val db = helper.readableDatabase
        val rs = db.rawQuery("SELECT * FROM USERS",null)

        //if a record exits then..
        if(rs.moveToNext())
        {
            //Tip: for edittext use ".setText" instead of ".text" :)
           username.setText(rs.getString(1))
            age.setText(rs.getString(2))
            weight.setText(rs.getString(3))
            height.setText(rs.getString(4))
        }

        //update User data
        updatebtn.setOnClickListener{

            //global variables
           /* User.Username =  username.text.toString()
            User.Age = age.text.toString().toInt()
            User.Weight  = weight.text.toString().toInt()
            User.Height = height.text.toString().toInt()
            User.UActivity =   sprUpdateActivites.getItemAtPosition(sprUpdateActivites.selectedItemPosition).toString()

            helper.UpdateData()*/


            var cv = ContentValues()


            cv.put("USERNAME",username.text.toString())
            cv.put("AGE",age.text.toString().toInt() )
            cv.put("HEIGHT", height.text.toString().toInt())
            cv.put("WEIGHT", weight.text.toString().toInt())
            cv.put("ACTIVITY",  sprUpdateActivites.getItemAtPosition(sprUpdateActivites.selectedItemPosition).toString())

            db.update("USERS",cv,"USERID = ?", arrayOf(rs.getString(0)))

            //global variables update
            User.Username =  username.text.toString()
            User.Age = age.text.toString().toInt()
            User.Weight  = weight.text.toString().toInt()
            User.Height = height.text.toString().toInt()
            User.UActivity =   sprUpdateActivites.getItemAtPosition(sprUpdateActivites.selectedItemPosition).toString()

            //alert venster
            var ad = AlertDialog.Builder(requireContext())
            ad.setTitle("Updata Data")
            ad.setMessage("Profile Updated Successfully!")
            ad.show()


        }

        return  v;
    }


}