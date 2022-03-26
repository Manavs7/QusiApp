package com.example.qusi.fragments

import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.qusi.R
import com.example.qusi.SQLiteHelper
import com.example.qusi.User
import org.w3c.dom.Text
import java.math.BigDecimal
import kotlin.math.round


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v: View = inflater.inflate(R.layout.fragment_home, container, false)

        val helper = SQLiteHelper(requireContext())
        val db = helper.readableDatabase
        val rs = db.rawQuery("SELECT * FROM USERS",null)

        if(rs.moveToNext())
        {
            val age = rs.getString(2).toDouble()
            val height = rs.getString(3).toDouble()
            val weight = rs.getString(4).toDouble()
            val gender = rs.getString(5)
            val uactivity = rs.getString(6)

            //berekening BMR mannen
            val dblBMR: Double

            if(gender == "male")
            {
                dblBMR = (10 * weight) + (6.25 * height) - (5 * age) + 5
            }
            //berekening BMR vrouwen
            else
            {
                dblBMR = (10 * weight) + (6.25 * height) - (5 * age) - 161
            }


            //TDEE TOATAL DAILY ENERGY EXPENDITURE (AANTAL KCAL DIE JE LICHAAM DAGELIJKS VERBRAND ZONDER ENIGE VORM VAN INZET)
            //Little or no exercie
            val  cTDEEL:Double = 1.2

            //Exercise 1-3 times/week
            val cTDEEM:Double =  1.375

            //Exercise 4-5 times/week
            val cTDEEH:Double = 1.55

            //Intense exercise 6-7 times/week
            val cTDEEI:Double =  1.9

            //Berekening maintenance caloriees
            val txtMaintenanceCal = v.findViewById<TextView>(R.id.txtMaintenanceCal)
            val maintenanceCal: Double

            when (uactivity) {
                "Little or no exercise" -> {
                    maintenanceCal =  dblBMR * cTDEEL
                    txtMaintenanceCal.text = "/${round(maintenanceCal)} cal"
                }
                "Moderate 1-3 times/week" -> {
                    maintenanceCal =  dblBMR * cTDEEM
                    txtMaintenanceCal.text = "/${round(maintenanceCal)} cal"
                }
                "Heavy 4-5 times/week" -> {
                    maintenanceCal =  dblBMR * cTDEEH
                    txtMaintenanceCal.text = "/${round(maintenanceCal)} cal"
                }
                "Daily/Intense 6-7 times/week" -> {
                    maintenanceCal = dblBMR * cTDEEI
                    txtMaintenanceCal.text = "/${round(maintenanceCal)} cal"
                }
                else->
                {
                    maintenanceCal = 0.0
                }
            }

            //Maintenance cal Progressbar
            val etCaloriesBurned = v.findViewById<EditText>(R.id.etCaloriesBurned)
            val calorieBurnedCal = etCaloriesBurned.text.toString().toDouble()
            val CaloriesBurnedPer = (calorieBurnedCal /maintenanceCal * 100.0).toInt()
            val ProgrMaintenanceCal = v.findViewById<ProgressBar>(R.id.ProgrMaintenance)
            ProgrMaintenanceCal.progress = CaloriesBurnedPer

            //berekening daily Protein goals
            val ProteinGoalG = weight * 2
            val ProteinGoalC = ProteinGoalG * 4
            val ProteinGoalPer = (ProteinGoalC/maintenanceCal * 100.0).toInt()

            val ProgrProtein = v.findViewById<ProgressBar>(R.id.ProgrProtein)
            ProgrProtein.progress = ProteinGoalPer

            val txtProteinPer = v.findViewById<TextView>(R.id.txtProteinPer)

            txtProteinPer.text = "$ProteinGoalPer%"

            //berekeing daily fat goals
            val FatGoalG = weight * 0.88;
            val FatGoalCal = FatGoalG * 4;


            //Berekening daily carbs goals
            val CarbsGoalCal = maintenanceCal - FatGoalCal
            val CarbsGoalG = CarbsGoalCal / 4;


            //Put data in global variables
            User.uMainetenanceCal = maintenanceCal.toInt()
            User.uProteinG = ProteinGoalG.toInt()
            User.uProteinCal = ProteinGoalC.toInt()
            User.uFatG = FatGoalG.toInt()
            User.uFatCal = FatGoalCal.toInt()
            User.uCarbsG = CarbsGoalG.toInt()
            User.uCarbsCal = CarbsGoalCal.toInt()

            //Put data in tabel Goal
           /* val cv = ContentValues()
            cv.put("MaintenanceCal", round(maintenanceCal))
            cv.put("ProteinCal", round(ProteinGoalC))
            cv.put("ProteinG", round(ProteinGoalG))
            cv.put("FatCal", round(FatGoalCal))
            cv.put("FatG", round(FatGoalG))
            cv.put("CarbsCal", round(CarbsGoalCal))
            cv.put("CarbsG", round(CarbsGoalG))

            var result = db.insert("GOAL",null,cv)
            if(result == (-1).toLong())
            {
                Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(context,"GOAL Inserted", Toast.LENGTH_SHORT).show()
            }*/


        }


        return  v;
    }


}