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

        txtProtein.text = "${User.uProteinG}g"
        txtFat.text = "${User.uFatG}g"
        txtCarbs.text = "${User.uCarbsG}g"
        return  v;
    }


}