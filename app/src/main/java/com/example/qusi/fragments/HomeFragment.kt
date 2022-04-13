package com.example.qusi.fragments

import android.app.AlertDialog
import android.content.ClipData
import android.content.ContentValues
import android.content.DialogInterface
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.qusi.R
import com.example.qusi.SQLiteHelper
import com.example.qusi.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.math.round
import android.R.id
import android.text.TextUtils
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.facebook.stetho.common.android.FragmentCompatUtil
import java.net.UnknownServiceException


class HomeFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v: View = inflater.inflate(R.layout.fragment_home, container, false)

        val helper = SQLiteHelper(requireContext())
        val db = helper.readableDatabase
        val rs = db.rawQuery("SELECT * FROM USERS", null)

        if (rs.moveToNext())
        {
            val age = rs.getString(2).toDouble()
            val height = rs.getString(3).toDouble()
            val weight = rs.getString(4).toDouble()
            val gender = rs.getString(5)
            val uactivity = rs.getString(6)

            //berekening BMR mannen
            val dblBMR: Double

            if (gender == "male") {
                dblBMR = (10 * weight) + (6.25 * height) - (5 * age) + 5
            }
            //berekening BMR vrouwen
            else {
                dblBMR = (10 * weight) + (6.25 * height) - (5 * age) - 161
            }


            //TDEE TOATAL DAILY ENERGY EXPENDITURE (AANTAL KCAL DIE JE LICHAAM DAGELIJKS VERBRAND ZONDER ENIGE VORM VAN INZET)
            //Little or no exercie
            val cTDEEL: Double = 1.2

            //Exercise 1-3 times/week
            val cTDEEM: Double = 1.375

            //Exercise 4-5 times/week
            val cTDEEH: Double = 1.55

            //Intense exercise 6-7 times/week
            val cTDEEI: Double = 1.9

            //Berekening maintenance caloriees
            val txtMaintenanceCal = v.findViewById<TextView>(R.id.txtMaintenanceCal)
            val maintenanceCal: Double

            when (uactivity) {
                "Little or no exercise" -> {
                    maintenanceCal = dblBMR * cTDEEL
                    txtMaintenanceCal.text = "/${round(maintenanceCal)} cal"
                }
                "Moderate 1-3 times/week" -> {
                    maintenanceCal = dblBMR * cTDEEM
                    txtMaintenanceCal.text = "/${round(maintenanceCal)} cal"
                }
                "Heavy 4-5 times/week" -> {
                    maintenanceCal = dblBMR * cTDEEH
                    txtMaintenanceCal.text = "/${round(maintenanceCal)} cal"
                }
                "Daily/Intense 6-7 times/week" -> {
                    maintenanceCal = dblBMR * cTDEEI
                    txtMaintenanceCal.text = "/${round(maintenanceCal)} cal"
                }
                else -> {
                    maintenanceCal = 0.0
                }
            }


            //berekening daily Protein goals
            val ProteinGoalG = weight * 2
            val ProteinGoalC = ProteinGoalG * 4









            //berekeing daily fat goals
            val FatGoalG = weight * 0.88;
            val FatGoalCal = FatGoalG * 4;
            val FatGoalPer = (FatGoalCal / maintenanceCal * 100.0).toInt()

            //val ConsumedFATG = rs.getString("").toDouble()
            //  val FatCalBurned  = ConsumedFatG / 4
            //FatGoalPer = FatCalBurned/FatGoalC

            //Berekening daily carbs goals
            val CarbsGoalCal = maintenanceCal - ProteinGoalC - FatGoalCal
            val CarbsGoalG = CarbsGoalCal / 4;
            val CarbsGoalPer = (CarbsGoalCal / maintenanceCal * 100.0).toInt()

            //val ConsumedCARBS = rs.getString("").toDouble()
            //  val CarbsCalBurned  = ConsumedCarbs / 4
            //CarbsGoalPer = CarbsCalBurned/CarbsGoalC

            //Put data in global variables
            User.uMainetenanceCal = maintenanceCal.toInt()
            User.uProteinG = ProteinGoalG.toInt()
            User.uProteinCal = ProteinGoalC.toInt()
            User.uFatG = FatGoalG.toInt()
            User.uFatCal = FatGoalCal.toInt()
            User.uCarbsG = CarbsGoalG.toInt()
            User.uCarbsCal = CarbsGoalCal.toInt()


            //update data in the table GOAL
            val cv = ContentValues()
            cv.put("MaintenanceCal", User.uMainetenanceCal)
            cv.put("ProteinCal", User.uProteinCal)
            cv.put("ProteinG", User.uProteinG)
            cv.put("FatCal", User.uFatCal)
            cv.put("FatG", User.uFatG)
            cv.put("CarbsCal", User.uCarbsCal)
            cv.put("CarbsG", User.uCarbsG)

            db.update("GOAL", cv, "GOALid = 1", null)


            //----------------------------------------------------FOOD EXPANDABLE LIST----------------------------------------------------------

            //BREAKFAST
            val addButton = v.findViewById<ImageView>(R.id.addBreakfast)
            val lstBreakfast = v.findViewById<ListView>(R.id.lstBreakfast)
            val arrBreakfast = mutableListOf<String>()
            val adapterBreakfast: ArrayAdapter<String> =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrBreakfast)
            lstBreakfast.adapter = adapterBreakfast



            addButton.setOnClickListener {
                //custum dialog linken aan layout
                val mDialogView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.addfooddiaolog, null)
                //alert dialog opbouwen
                var mBuiler = AlertDialog.Builder(requireContext())
                    .setView(mDialogView)
                    .setTitle("Add Food To Your Diary")
                    .setPositiveButton(
                        "Done",
                        DialogInterface.OnClickListener { dialogInterface, i ->

                            //Variables in custum Dialog
                            val etfoodname = mDialogView.findViewById<AutoCompleteTextView>(R.id.etFoodname)
                            val etfoodProteinG = mDialogView.findViewById<EditText>(R.id.etProteing)
                            val etfoodFatG = mDialogView.findViewById<EditText>(R.id.etFatg)
                            val etfoodCarbsG = mDialogView.findViewById<EditText>(R.id.etCarbsg)
                            //val etFoodAmountG = mDialogView.findViewById<EditText>(R.id.FoodAmountG)

                            val cvFood = ContentValues()
                            cvFood.put("FOODNAME", etfoodname.text.toString())
                            cvFood.put("FOODPROTEING", etfoodProteinG.text.toString())
                            cvFood.put("FOODFATG", etfoodFatG.text.toString())
                            cvFood.put("FOODCARBSG", etfoodCarbsG.text.toString())

                            //Fout tolerantie Food
                            if(etfoodname.text.toString() == "")
                            {


                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food cannot be empty!")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }

                            //fout tolerantie Food Protein
                            else if (etfoodProteinG.length() == 0 || etfoodProteinG.text.toString().toDouble()<0.1)
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food protein cannot be 0 or empty")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }

                            else if (etfoodFatG.length() == 0 || etfoodFatG.text.toString().toDouble() < 0.1)
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food Fat cannot be 0 or empty")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }

                            else if (etfoodCarbsG.length() == 0 || etfoodCarbsG.text.toString().toDouble() < 0.1)
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food Carbs cannot be 0 or empty")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }
                            else
                            {
                                db.insert("FOODBREAKFAST", null, cvFood)
                                arrBreakfast.add(etfoodname.text.toString())
                                adapterBreakfast.notifyDataSetChanged()
                            }

                            //reload fragment
                            val navController: NavController = requireActivity().findNavController(R.id.container_fragment)
                            navController.run {
                                popBackStack()
                                navigate(R.id.homeFragment)}

                        })

                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                mBuiler.show()

            }

            lstBreakfast.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->


                    // show an alert dialog to confirm
                    val clickedItem = parent.getItemAtPosition(position).toString()

                    MaterialAlertDialogBuilder(requireContext()).apply {
                        setTitle("Remove Food")
                        setMessage("Do you want to remove $clickedItem from your diary?")
                        setPositiveButton("Delete") { _, _ ->
                            // remove clicked item from second list view
                            arrBreakfast.removeAt(position)
                            adapterBreakfast.notifyDataSetChanged()


                             db.delete("FOODBREAKFAST", "FOODNAME = '$clickedItem'", null)

                            //reload fragment
                            val navController: NavController = requireActivity().findNavController(R.id.container_fragment)
                            navController.run {
                                popBackStack()
                                navigate(R.id.homeFragment)}


                        }
                        setNeutralButton("Cancel") { _, _ -> }
                    }.create().show()

                }



            //display breakfast data in list + updating Macronutrienten
            val rsBREAKFAST = db.rawQuery("SELECT * FROM FOODBREAKFAST", null)
           while (rsBREAKFAST.moveToNext()) {
                val foodname = rsBREAKFAST.getString(1)
                arrBreakfast.add(foodname)
                adapterBreakfast.notifyDataSetChanged()

            }

            //execute querry for sum database
            val sumFoodProtein = db.rawQuery("SELECT SUM(FOODPROTEING) FROM FOODBREAKFAST", null)
            val sumFoodFat = db.rawQuery("SELECT SUM(FOODFATG) FROM FOODBREAKFAST", null)
            val sumFoodCarbs = db.rawQuery("SELECT SUM(FOODCARBSG) FROM FOODBREAKFAST", null)

            var consumedprotein: Int
            var consumedFat: Int
            var consumedCarbs: Int
            var totalFoodCalories: Int
            if(sumFoodProtein.moveToFirst())
            {
                consumedprotein = sumFoodProtein.getDouble(0).toInt()
                User.uFoodProteinCal = consumedprotein * 4
            }
            if(sumFoodFat.moveToFirst())
            {
                consumedFat = sumFoodFat.getDouble(0).toInt()
                User.uFoodFatCal = consumedFat * 9
            }

            if(sumFoodCarbs.moveToFirst())
            {
                consumedCarbs = sumFoodCarbs.getDouble(0).toInt()
                User.uFoodCarbsCal = consumedCarbs * 4
            }

            totalFoodCalories = User.uFoodProteinCal + User.uFoodFatCal + User.uFoodCarbsCal
            User.uTotalFoodCal = totalFoodCalories


            //progress bar maintenance cal-------------------------------------------------------------------------------------------
            val txtCaloriesBurned = v.findViewById<TextView>(R.id.txtCaloriesBurned)
            txtCaloriesBurned.text = User.uTotalFoodCal.toString()
            var CaloriesBurnedPer = ( txtCaloriesBurned.text.toString().toDouble() / maintenanceCal * 100.0).toInt()
            val ProgrMaintenanceCal = v.findViewById<ProgressBar>(R.id.ProgrMaintenance)
            ProgrMaintenanceCal.progress = CaloriesBurnedPer


            //progress bar Protein cal-------------------------------------------------------------------------------------------------
            var ProteinGoalPer = (User.uFoodProteinCal/ ProteinGoalC * 100.0).toInt()
            val ProgrProteinCal = v.findViewById<ProgressBar>(R.id.ProgrProtein)
            ProgrProteinCal.progress = ProteinGoalPer
           val txtProteinPer = v.findViewById<TextView>(R.id.txtProteinPer)
            txtProteinPer.text = "$ProteinGoalPer%"

            //progress bar Fat Cal



            //progress bar Carbs Cal






        //--------------------------------------------Lunch list------------------------------------------------------------
            //Lunch
            val addButtonLunch = v.findViewById<ImageView>(R.id.addLunch)
            val lstLunch= v.findViewById<ListView>(R.id.lstLunch)
            val arrLunch = mutableListOf<String>()
            val adapterLunch: ArrayAdapter<String> =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrLunch)
            lstLunch.adapter = adapterLunch

            addButtonLunch.setOnClickListener {
                //custum dialog linken aan layout
                val mDialogView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.addfooddiaolog, null)
                //alert dialog opbouwen
                var mBuiler = AlertDialog.Builder(requireContext())
                    .setView(mDialogView)
                    .setTitle("Add Food To Your Diary")
                    .setPositiveButton(
                        "Done",
                        DialogInterface.OnClickListener { dialogInterface, i ->

                            val etfoodname = mDialogView.findViewById<AutoCompleteTextView>(R.id.etFoodname)
                            val etfoodProteinG = mDialogView.findViewById<EditText>(R.id.etProteing)
                            val etfoodFatG = mDialogView.findViewById<EditText>(R.id.etFatg)
                            val etfoodCarbsG = mDialogView.findViewById<EditText>(R.id.etCarbsg)
                           // val etFoodAmountG = mDialogView.findViewById<EditText>(R.id.FoodAmountG)

                            val cvFood = ContentValues()
                            cvFood.put("FOODNAME", etfoodname.text.toString())
                            cvFood.put("FOODPROTEING", etfoodProteinG.text.toString())
                            cvFood.put("FOODFATG", etfoodFatG.text.toString())
                            cvFood.put("FOODCARBSG", etfoodCarbsG.text.toString())
                            cvFood.put("FOODNAME", etfoodname.text.toString())
                            //check if food is empty
                            if(etfoodname.text.toString() == "")
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food cannot be empty!")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }

                            //fout tolerantie Food Protein
                            else if (etfoodProteinG.length() == 0 || etfoodProteinG.text.toString().toDouble()<0.1)
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food protein cannot be 0 or empty")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }

                            else if (etfoodFatG.length() == 0 || etfoodFatG.text.toString().toDouble() < 0.1)
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food Fat cannot be 0 or empty")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }

                            else if (etfoodCarbsG.length() == 0 || etfoodCarbsG.text.toString().toDouble() < 0.1)
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food Carbs cannot be 0 or empty")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }
                            else
                            {
                                db.insert("FOODLUNCH", null, cvFood)
                                arrLunch.add(etfoodname.text.toString())
                                adapterLunch.notifyDataSetChanged()
                            }

                        })

                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                mBuiler.show()

            }



            lstLunch.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->


                    // show an alert dialog to confirm
                    val clickedItem = parent.getItemAtPosition(position).toString()

                    MaterialAlertDialogBuilder(requireContext()).apply {
                        setTitle("Remove Food")
                        setMessage("Do you want to remove $clickedItem from your diary?")
                        setPositiveButton("Delete") { _, _ ->
                            // remove clicked item from second list view
                            arrLunch.removeAt(position)
                            adapterLunch.notifyDataSetChanged()


                            db.delete("FOODLUNCH", "FOODNAME = '$clickedItem'", null)
                        }
                        setNeutralButton("Cancel") { _, _ -> }
                    }.create().show()

                }


            //display LUNCH data in list
            val rsLUNCH = db.rawQuery("SELECT * FROM FOODLUNCH", null)
            while (rsLUNCH.moveToNext()) {
                val foodname = rsLUNCH.getString(1)
                arrLunch.add(foodname)
                adapterLunch.notifyDataSetChanged()
            }

            //--------------------------------------------Snacks list------------------------------------------------------------
            //Snacks variables
            val addButtonSnacks = v.findViewById<ImageView>(R.id.addSnacks)
            val lstSnacks= v.findViewById<ListView>(R.id.lstSnacks)
            val arrSnacks = mutableListOf<String>()
            val adapterSnacks: ArrayAdapter<String> =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrSnacks)
            lstSnacks.adapter = adapterSnacks


            addButtonSnacks.setOnClickListener {
                //custum dialog linken aan layout
                val mDialogView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.addfooddiaolog, null)
                //alert dialog opbouwen
                var mBuiler = AlertDialog.Builder(requireContext())
                    .setView(mDialogView)
                    .setTitle("Add Food To Your Diary")
                    .setPositiveButton(
                        "Done",
                        DialogInterface.OnClickListener { dialogInterface, i ->

                            val etfoodname = mDialogView.findViewById<AutoCompleteTextView>(R.id.etFoodname)
                            val etfoodProteinG = mDialogView.findViewById<EditText>(R.id.etProteing)
                            val etfoodFatG = mDialogView.findViewById<EditText>(R.id.etFatg)
                            val etfoodCarbsG = mDialogView.findViewById<EditText>(R.id.etCarbsg)
                           // val etFoodAmountG = mDialogView.findViewById<EditText>(R.id.FoodAmountG)

                            val cvFood = ContentValues()
                            cvFood.put("FOODNAME", etfoodname.text.toString())
                            cvFood.put("FOODPROTEING", etfoodProteinG.text.toString())
                            cvFood.put("FOODFATG", etfoodFatG.text.toString())
                            cvFood.put("FOODCARBSG", etfoodCarbsG.text.toString())
                            cvFood.put("FOODNAME", etfoodname.text.toString())
                            cvFood.put("FOODNAME", etfoodname.text.toString())

                            //check if food is empty
                            if(etfoodname.text.toString() == "")
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food cannot be empty!")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }
                            //fout tolerantie Food Protein
                            else if (etfoodProteinG.length() == 0 || etfoodProteinG.text.toString().toDouble()<0.1)
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food protein cannot be 0 or empty")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }

                            else if (etfoodFatG.length() == 0 || etfoodFatG.text.toString().toDouble() < 0.1)
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food Fat cannot be 0 or empty")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }

                            else if (etfoodCarbsG.length() == 0 || etfoodCarbsG.text.toString().toDouble() < 0.1)
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food Carbs cannot be 0 or empty")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }
                            else
                            {
                                db.insert("FOODSNACKS", null, cvFood)
                                arrSnacks.add(etfoodname.text.toString())
                                adapterSnacks.notifyDataSetChanged()
                            }

                        })

                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                mBuiler.show()

            }


            lstSnacks.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->


                    // show an alert dialog to confirm
                    val clickedItem = parent.getItemAtPosition(position).toString()

                    MaterialAlertDialogBuilder(requireContext()).apply {
                        setTitle("Remove Food")
                        setMessage("Do you want to remove $clickedItem from your diary?")
                        setPositiveButton("Delete") { _, _ ->
                            // remove clicked item from second list view
                            arrSnacks.removeAt(position)
                            adapterSnacks.notifyDataSetChanged()


                            db.delete("FOODSNACKS", "FOODNAME = '$clickedItem'", null)
                        }
                        setNeutralButton("Cancel") { _, _ -> }
                    }.create().show()

                }


            //display SNACKS data in list
            val rsSNACKS = db.rawQuery("SELECT * FROM FOODSNACKS", null)
            while (rsSNACKS.moveToNext()) {
                val foodname = rsSNACKS.getString(1)
                arrSnacks.add(foodname)
                adapterSnacks.notifyDataSetChanged()
            }


        //--------------------------------------------DINNER list------------------------------------------------------------
            //DINNER variables
            val addButtonDinner = v.findViewById<ImageView>(R.id.addDinner)
            val lstDinner= v.findViewById<ListView>(R.id.lstDinner)
            val arrDinner = mutableListOf<String>()
            val adapterDinner: ArrayAdapter<String> =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrDinner)
            lstDinner.adapter = adapterDinner


            addButtonDinner.setOnClickListener {
                //custum dialog linken aan layout
                val mDialogView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.addfooddiaolog, null)
                //alert dialog opbouwen
                var mBuiler = AlertDialog.Builder(requireContext())
                    .setView(mDialogView)
                    .setTitle("Add Food To Your Diary")
                    .setPositiveButton(
                        "Done",
                        DialogInterface.OnClickListener { dialogInterface, i ->

                            val etfoodname = mDialogView.findViewById<AutoCompleteTextView>(R.id.etFoodname)
                            val etfoodProteinG = mDialogView.findViewById<EditText>(R.id.etProteing)
                            val etfoodFatG = mDialogView.findViewById<EditText>(R.id.etFatg)
                            val etfoodCarbsG = mDialogView.findViewById<EditText>(R.id.etCarbsg)
                            //val etFoodAmountG = mDialogView.findViewById<EditText>(R.id.FoodAmountG)

                            val cvFood = ContentValues()
                            cvFood.put("FOODNAME", etfoodname.text.toString())
                            cvFood.put("FOODPROTEING", etfoodProteinG.text.toString())
                            cvFood.put("FOODFATG", etfoodFatG.text.toString())
                            cvFood.put("FOODCARBSG", etfoodCarbsG.text.toString())
                            cvFood.put("FOODNAME", etfoodname.text.toString())
                            cvFood.put("FOODNAME", etfoodname.text.toString())

                            //check if food is empty
                            if(etfoodname.text.toString() == "")
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food cannot be empty!")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }
                            //fout tolerantie Food Protein
                            else if (etfoodProteinG.length() == 0 || etfoodProteinG.text.toString().toDouble()<0.1)
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food protein cannot be 0 or empty")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }

                            else if (etfoodFatG.length() == 0 || etfoodFatG.text.toString().toDouble() < 0.1)
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food Fat cannot be 0 or empty")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }

                            else if (etfoodCarbsG.length() == 0 || etfoodCarbsG.text.toString().toDouble() < 0.1)
                            {
                                var errorAlert = AlertDialog.Builder(requireContext())
                                    .setTitle("Food Carbs cannot be 0 or empty")
                                    .setPositiveButton("Okay",DialogInterface.OnClickListener { dialogInterface, i ->
                                        dialogInterface.dismiss()
                                    })
                                errorAlert.show()
                            }
                            else
                            {
                                db.insert("FOODDINNER", null, cvFood)
                                arrDinner.add(etfoodname.text.toString())
                                adapterDinner.notifyDataSetChanged()
                            }

                        })

                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                mBuiler.show()

            }


            lstDinner.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->


                    // show an alert dialog to confirm
                    val clickedItem = parent.getItemAtPosition(position).toString()

                    MaterialAlertDialogBuilder(requireContext()).apply {
                        setTitle("Remove Food")
                        setMessage("Do you want to remove $clickedItem from your diary?")
                        setPositiveButton("Delete") { _, _ ->
                            // remove clicked item from second list view
                            arrDinner.removeAt(position)
                            adapterDinner.notifyDataSetChanged()


                            db.delete("FOODDINNER", "FOODNAME = '$clickedItem'", null)
                        }
                        setNeutralButton("Cancel") { _, _ -> }
                    }.create().show()

                }


            //display DINNER data in list
            val rsDINNER = db.rawQuery("SELECT * FROM FOODDINNER", null)
            while (rsDINNER.moveToNext()) {
                val foodname = rsDINNER.getString(1)
                arrDinner.add(foodname)
                adapterDinner.notifyDataSetChanged()
            }

            //Updating Macronutrienten



        }

            return v;
     }




   }
