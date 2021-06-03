package com.example.myprohelper.Db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.*


class DataBaseHelperClass(context: Context) {
    private val openHelper: SQLiteOpenHelper
    private var database: SQLiteDatabase? = null

    /**
     * Open the database connection.
     */
    fun open() {
        database = openHelper.writableDatabase
    }

    /**
     * Close the database connection.
     */
    fun close() {
        if (database != null) {
            database!!.close()
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    fun versiondb(): String? {
        var id: String? = null
        val cursor = database!!.rawQuery("SELECT id FROM Versions Order by id desc limit 1", null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            id = cursor.getString(0)
            cursor.moveToNext()
        }
        cursor.close()
        return id
    }

    fun insertAssets(): Boolean {
        val db = database
        val contentValues = ContentValues()

        contentValues.put("AssetName", "car 10")
        contentValues.put("Description", "fast also 445 6")
        contentValues.put("ModelInfo", "audi a8")
        contentValues.put("DatePurchased", "2/22/2021 8:58:33 PM")
        contentValues.put("SerialNumber", "123")
        contentValues.put("DateCreated", "1/1/1900 12:00:00 AM")
        contentValues.put("DateModified", "1/1/1900 12:00:00 AM")
        contentValues.put("PurchasePrice", 3)
        contentValues.put("AssetType", 4)
        contentValues.put("LatestMaintenanceDate", "2/22/2021 8:58:33 PM")
        contentValues.put("Mileage", 0)
        contentValues.put("HoursUsed", 0)
        contentValues.put("SampleAsset", 1)
        contentValues.put("Removed", 0)
        contentValues.put("RemovedDate", "1/1/1900 12:00:00 AM")
        contentValues.put("NumberOfAttachments", 0)
        db!!.insert("Assets", null, contentValues)
        return true
    }


    companion object {
        private var instance: DataBaseHelperClass? = null


        fun getInstance(context: Context?): DataBaseHelperClass? {
            if (Companion.instance == null) {
                Companion.instance = DataBaseHelperClass(context!!)
            }
            return Companion.instance
        }
    }

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    init {
        openHelper = DatabaseOpenHelper(context)
    }
}