package com.example.qusi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.qusi.R
import com.example.qusi.User
import org.w3c.dom.Text


class InfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v:View = inflater.inflate(R.layout.fragment_info, container, false)

        val txtProtein = v.findViewById<TextView>(R.id.txtProtein)
        val txtFat = v.findViewById<TextView>(R.id.txtFat)
        val txtCarbs = v.findViewById<TextView>(R.id.txtCarbs)

        val txtProteinCal = v.findViewById<TextView>(R.id.txtProteinCal)
        val txtFatCal = v.findViewById<TextView>(R.id.txtFatCal)
        val txtCarbCal = v.findViewById<TextView>(R.id.txtCarbCal)
        val txtInfoMaintenanceCalorie = v.findViewById<TextView>(R.id.txtInfoMaintenanceCalorie)

        txtProtein.text = "${User.uProteinG}g"
        txtFat.text = "${User.uFatG}g"
        txtCarbs.text = "${User.uCarbsG}g"

        txtProteinCal.text = "${User.uProteinCal}cal"
        txtFatCal.text = "${User.uFatCal}cal"
        txtCarbCal.text = "${User.uCarbsCal}cal"

        txtInfoMaintenanceCalorie.text = "${User.uMainetenanceCal} Cal / day"

        val consumedProteinCal = v.findViewById<TextView>(R.id.consumedProteinCal)
        val consumedFatCal = v.findViewById<TextView>(R.id.consumedFatCal)
        val consumedCarbCal = v.findViewById<TextView>(R.id.consumedCarbsCal)
        val totalfoodcal = v.findViewById<TextView>(R.id.TotalFoodCalories)
        consumedProteinCal.text = User.uFoodProteinCal.toString()
        consumedFatCal.text = User.uFoodFatCal.toString()
        consumedCarbCal.text = User.uFoodCarbsCal.toString()
        totalfoodcal.text  =User.uTotalFoodCal.toString()
        return  v;
    }


}