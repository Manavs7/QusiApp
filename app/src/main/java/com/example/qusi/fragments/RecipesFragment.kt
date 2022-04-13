package com.example.qusi.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.qusi.R


class RecipesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v:View = inflater.inflate(R.layout.fragment_recipes, container, false)
        //Vinden van View
        val icosearchKCR = v.findViewById<ImageView>(R.id.icosearchKCR)

        //bij button click ga naar webpagina
        icosearchKCR.setOnClickListener {
            val url = "https://dagelijksekost.een.be/gerechten/kip-in-currysaus-met-rijst"
            val newIntent = Intent(Intent.ACTION_VIEW)
            newIntent.data = Uri.parse(url)
            startActivity(newIntent)
        }
        return v;
    }


}