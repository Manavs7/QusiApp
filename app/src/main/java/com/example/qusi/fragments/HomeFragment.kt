package com.example.qusi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qusi.R


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v: View = inflater.inflate(R.layout.fragment_home, container, false)
        
        /* 
         //Database oproepen
         val helper = SQLiteHelper(requireContext())
        val db = helper.readableDatabase
        val rs = db.rawQuery("SELECT * FROM USERS",null)
        //Varaibeles data user
         val username = rs.getString(1).toDouble()
        val age = rs.getString(2).toDouble()
        val weight = rs.getString(3).toDouble()
        val height = rs.getString(4).toDouble()
        val gender = rs.getString(5)
        
        //berekening BMR mannen 
        val BMR:Double
        if(gender == "male")
        {
            BMR = (10 * weight) + (6.25 * height) - (5 * age) + 5;
        }
        
        //berekening BMR mannen 
        if(gender == "female")
        {
            BMR = (10 * weight) + (6.25 * height) - (5 * age) - 161;
        }
        
        //   //TDEE TOATAL DAILY ENERGY EXPENDITURE (AANTAL KCAL DIE JE LICHAAM DAGELIJKS VERBRAND ZONDER ENIGE VORM VAN INZET)
         */

        return  v;
    }


}
