package com.example.qusi

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class SQLiteHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object{

        private  const val DATABASE_VERSION = 1
        private  const val DATABASE_NAME = "qusi"

       /* private  const val COL_USERNAME = "Username"
        private  const val COL_AGE = "Age"
        private  const val COL_HEIGHT = "Height"
        private  const val COL_WEIGHT = "Weight"
        private  const val COL_GENDER = "Gender"
        private  const val COL_ACIVITY  = "Activity" */
    }

    override fun onCreate(db: SQLiteDatabase?) {

        //Create Users table
        db?.execSQL("CREATE TABLE USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT, AGE INTEGER, HEIGHT INTEGER, WEIGHT INTEGER, GENDER TEXT, ACTIVITY TEXT)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       db!!.execSQL("DROP TABLE IF EXISTS USERS")
        onCreate(db)
    }

    fun InsertData()
    {
        val db = this.writableDatabase
        var cv = ContentValues()

        cv.put("USERNAME",User.Username)
        cv.put("AGE", User.Age)
        cv.put("HEIGHT", User.Height)
        cv.put("WEIGHT", User.Weight)
        cv.put("GENDER", User.Gender)
        cv.put("ACTIVITY", User.UActivity)

        var result = db.insert("USERS",null,cv)
        if(result == (-1).toLong())
        {
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context,"Data Inserted",Toast.LENGTH_SHORT).show()
        }
    }

    fun UpdateData()
    {
        /*val db = this.writableDatabase
        var cv = ContentValues()
        val rs = db.rawQuery("SELECT * FROM USERS",null)

        cv.put("USERNAME",User.Username)
        cv.put("AGE", User.Age)
        cv.put("HEIGHT", User.Height)
        cv.put("WEIGHT", User.Weight)
        cv.put("ACTIVITY", User.UActivity)

        db.update("USERS",cv,"USERID = ?", arrayOf(rs.getString(0)))*/
    }

}