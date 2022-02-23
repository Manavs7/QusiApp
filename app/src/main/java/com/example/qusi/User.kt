package com.example.qusi

import android.widget.EditText

class User {

    var Username: String =""
    var Age: Int = 0
    var Weight: Int = 0
    var Height: Int = 0

    constructor(pName: String, pAge: Int, pWeight: Int, pHeight: Int)
    {
        this.Username = pName
        this.Age = pAge
        this.Weight = pWeight
        this.Height = pHeight
    }
}