package com.example.myprohelper.Db

import android.app.Notification
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myprohelper.Model.Message
import com.example.myprohelper.Model.MyProhelper

class MyProhelperDatabaseAdapter(context: Context) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "myprohelperSample.db"
        private val TABLE_NAME = "AppEvents"
        private val KEY_ID = "id"
        private val KEY_APP_RUN_COUNT = "appRunCount"
        private val KEY_TITLE = "title"
        private val KEY_DESCRIPTION = "description"
    }

    private val mContext = context
    private val databaseHelper = DatabaseHelper(mContext)
    private val MyProhelperList = ArrayList<MyProhelper>()

    fun insertData(count: String, title: String, description: String): Long {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_APP_RUN_COUNT, count)
        contentValues.put(KEY_TITLE, title)
        contentValues.put(KEY_DESCRIPTION, description)
        val id = db.insert(TABLE_NAME, null, contentValues)
        return id
    }

    fun updateData(count: String, title: String, description: String): Int {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_APP_RUN_COUNT, count)
        contentValues.put(KEY_TITLE, title)
        contentValues.put(KEY_DESCRIPTION, description)
        val WhereArgs = arrayOf<String>(KEY_ID.toString())
        val updatedRows = db.update(TABLE_NAME, contentValues, KEY_ID + " =?", WhereArgs)
        return updatedRows
    }

    fun getAllData(): ArrayList<MyProhelper> {
        MyProhelperList.clear()
        val db = databaseHelper.writableDatabase
        val columns = arrayOf<String>(KEY_ID, KEY_APP_RUN_COUNT, KEY_TITLE, KEY_DESCRIPTION)
        val cursor: Cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val myProhelper = MyProhelper()
            val index1 = cursor.getColumnIndex(KEY_APP_RUN_COUNT)
            val index2 = cursor.getColumnIndex(KEY_TITLE)
            val index3 = cursor.getColumnIndex(KEY_DESCRIPTION)
            val index4 = cursor.getColumnIndex(KEY_ID)
            myProhelper.appRunCount = cursor.getInt(index1)
            myProhelper.title = cursor.getString(index2)
            myProhelper.description = cursor.getString(index3)
            myProhelper.id = cursor.getInt(index4)
            MyProhelperList.add(myProhelper)
        }
        return MyProhelperList
    }

    class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        private val mContext = context
        override fun onCreate(db: SQLiteDatabase?) {
            val querry =
                "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_APP_RUN_COUNT + " INTEGER, " + KEY_TITLE + " TEXT, " + KEY_DESCRIPTION + " VARCHAR(255));"
            db?.execSQL(querry)
            if (db != null) {
                Message.message(mContext, "Database Successfully created")
            }
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
            onCreate(db)
        }

    }

}
