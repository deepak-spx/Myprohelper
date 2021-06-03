package com.example.myprohelper.Db

import android.app.Notification
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myprohelper.Model.*

class SettingsDatabaseAdapter(context: Context) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Settings.db"
        private val KEY_ID = "rowID"
        private val KEY_APP_RUN_COUNT = "appRunCount"
        private val KEY_VERSION = "version"
        private val KEY_DEVICEID = "deviceID"
        private val KEY_DEVICECODEEXPIRATION = "DeviceCodeExpiration"
        private val KEY_SUBDOMAIN = "SubDomain"
        private val KEY_DEVICEGUID = "DeviceGUID"
        private val KEY_UPDBCODE1 = "UPDBCode1"
        private val KEY_UPDBCODE2 = "UPDBCode2"
        private val KEY_UPDBCODE3 = "UPDBCode3"
        private val KEY_COMPANYID = "CompanyID"
        private val TABLE_APPRUNCOUNT = "settings_AppRunCount"
        private val TABLE_SERVERACCESS = "settings_ServerAccess"
        private val TABLE_DBVERSION = "settings_dbVersion"
        private val TABLE_DEVICEID = "settings_deviceID"

    }

    private val mContext = context
    private val databaseHelper = DatabaseHelper(mContext)
    private val AppRunCountList = ArrayList<AppRunCount>()
    private val ServerAcessList = ArrayList<ServerAcess>()
    private val DbVersionList = ArrayList<DbVersion>()
    private val DeviceIdList = ArrayList<DeviceId>()

    fun insertAppRunCount(count: Int): Long {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_APP_RUN_COUNT, count)
        val id = db.insert(TABLE_APPRUNCOUNT, null, contentValues)
        return id
    }

    fun insertServerAccess(
        device_code_expiration: String,
        subDomain: String,
        deviceGuid: String,
        updbcode1: String,
        updbcode2: String,
        updbcode3: String,
        companyId: String
    ): Long {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_DEVICECODEEXPIRATION, device_code_expiration)
        contentValues.put(KEY_SUBDOMAIN, subDomain)
        contentValues.put(KEY_DEVICEGUID, deviceGuid)
        contentValues.put(KEY_UPDBCODE1, updbcode1)
        contentValues.put(KEY_UPDBCODE2, updbcode2)
        contentValues.put(KEY_UPDBCODE3, updbcode3)
        contentValues.put(KEY_COMPANYID, companyId)
        val id = db.insert(TABLE_SERVERACCESS, null, contentValues)
        return id
    }

    fun insertDbVersion(version: String): Long {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_VERSION, version)
        val id = db.insert(TABLE_DBVERSION, null, contentValues)
        return id
    }

    fun insertDeviceId(deviceId: Int): Long {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_DEVICEID, deviceId)
        val id = db.insert(TABLE_DEVICEID, null, contentValues)
        return id
    }


    fun updateAppRunCount(count: Int): Int {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_APP_RUN_COUNT, count)
        val WhereArgs = arrayOf<String>(KEY_ID.toString())
        val updatedRows = db.update(TABLE_APPRUNCOUNT, contentValues, KEY_ID + " =?", WhereArgs)
        return updatedRows
    }

    fun updateServerAccess(
        device_code_expiration: String,
        subDomain: String,
        deviceGuid: String,
        updbcode1: String,
        updbcode2: String,
        updbcode3: String,
        companyId: String
    ): Int {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_DEVICECODEEXPIRATION, device_code_expiration)
        contentValues.put(KEY_SUBDOMAIN, subDomain)
        contentValues.put(KEY_DEVICEGUID, deviceGuid)
        contentValues.put(KEY_UPDBCODE1, updbcode1)
        contentValues.put(KEY_UPDBCODE2, updbcode2)
        contentValues.put(KEY_UPDBCODE3, updbcode3)
        contentValues.put(KEY_COMPANYID, companyId)
        val WhereArgs = arrayOf<String>(KEY_ID.toString())
        val updatedRows = db.update(TABLE_SERVERACCESS, contentValues, KEY_ID + " =?", WhereArgs)
        return updatedRows
    }

    fun updateDbVersion(version: Int): Int {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_VERSION, version)
        val WhereArgs = arrayOf<String>(KEY_ID.toString())
        val updatedRows = db.update(TABLE_DBVERSION, contentValues, KEY_ID + " =?", WhereArgs)
        return updatedRows
    }

    fun updateDeviceId(deviceId: Int): Int {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_DEVICEID, deviceId)
        val WhereArgs = arrayOf<String>(KEY_ID.toString())
        val updatedRows = db.update(TABLE_DEVICEID, contentValues, KEY_ID + " =?", WhereArgs)
        return updatedRows
    }

    fun getAppRunCountData(): ArrayList<AppRunCount> {
        AppRunCountList.clear()
        val db = databaseHelper.writableDatabase
        val columns = arrayOf<String>(KEY_ID, KEY_APP_RUN_COUNT)
        val cursor: Cursor = db.query(TABLE_APPRUNCOUNT, columns, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val appRunCount = AppRunCount()
            val index1 = cursor.getColumnIndex(KEY_APP_RUN_COUNT)
            val index2 = cursor.getColumnIndex(KEY_ID)
            appRunCount.appRunCount = cursor.getInt(index1)
            appRunCount.id = cursor.getInt(index2)
            AppRunCountList.add(appRunCount)
        }
        return AppRunCountList
    }

    fun getServerAccessData(): ArrayList<ServerAcess> {
        ServerAcessList.clear()
        val db = databaseHelper.writableDatabase
        val columns = arrayOf<String>(
            KEY_ID, KEY_DEVICECODEEXPIRATION, KEY_SUBDOMAIN,
            KEY_DEVICEGUID, KEY_UPDBCODE1, KEY_UPDBCODE2, KEY_UPDBCODE3, KEY_COMPANYID
        )
        val cursor: Cursor = db.query(TABLE_APPRUNCOUNT, columns, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val serverAcess = ServerAcess()
            val index1 = cursor.getColumnIndex(KEY_ID)
            val index2 = cursor.getColumnIndex(KEY_DEVICECODEEXPIRATION)
            val index3 = cursor.getColumnIndex(KEY_SUBDOMAIN)
            val index4 = cursor.getColumnIndex(KEY_DEVICEGUID)
            val index5 = cursor.getColumnIndex(KEY_UPDBCODE1)
            val index6 = cursor.getColumnIndex(KEY_UPDBCODE2)
            val index7 = cursor.getColumnIndex(KEY_UPDBCODE3)
            val index8 = cursor.getColumnIndex(KEY_COMPANYID)
            serverAcess.id = cursor.getInt(index1)
            serverAcess.device_code_expiration = cursor.getString(index2)
            serverAcess.subDomain = cursor.getString(index3)
            serverAcess.deviceguid = cursor.getString(index4)
            serverAcess.updbcode1 = cursor.getString(index5)
            serverAcess.updbcode2 = cursor.getString(index6)
            serverAcess.updbcode3 = cursor.getString(index7)
            serverAcess.companyId = cursor.getString(index8)
            ServerAcessList.add(serverAcess)
        }
        return ServerAcessList
    }

    fun getDbVersionData(): ArrayList<DbVersion> {
        DbVersionList.clear()
        val db = databaseHelper.writableDatabase
        val columns = arrayOf<String>(KEY_ID, KEY_VERSION)
        val cursor: Cursor = db.query(TABLE_DBVERSION, columns, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val dbVersion = DbVersion()
            val index1 = cursor.getColumnIndex(KEY_ID)
            val index2 = cursor.getColumnIndex(KEY_VERSION)
            dbVersion.id = cursor.getInt(index1)
            dbVersion.version = cursor.getInt(index2)
            DbVersionList.add(dbVersion)
        }
        return DbVersionList
    }

    fun getDeviceIdData(): ArrayList<DeviceId> {
        DeviceIdList.clear()
        val db = databaseHelper.writableDatabase
        val columns = arrayOf<String>(KEY_ID, KEY_DEVICEID)
        val cursor: Cursor = db.query(TABLE_DEVICEID, columns, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val deviceId = DeviceId()
            val index1 = cursor.getColumnIndex(KEY_ID)
            val index2 = cursor.getColumnIndex(KEY_DEVICEID)
            deviceId.id = cursor.getInt(index1)
            deviceId.deviceID = cursor.getInt(index2)
            DeviceIdList.add(deviceId)
        }
        return DeviceIdList
    }


    class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        private val mContext = context
        override fun onCreate(db: SQLiteDatabase?) {

            val query_apprun =
                "CREATE TABLE " + TABLE_APPRUNCOUNT + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_APP_RUN_COUNT + " INTEGER);"
            val query_server_acesss =
                "CREATE TABLE " + TABLE_SERVERACCESS + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DEVICECODEEXPIRATION + " VARCHAR, " + KEY_SUBDOMAIN + " VARCHAR, " + KEY_DEVICEGUID + " VARCHAR, " + KEY_UPDBCODE1 + " VARCHAR, " + KEY_UPDBCODE2 + " VARCHAR, " + KEY_UPDBCODE3 + " VARCHAR, " + KEY_COMPANYID + " VARCHAR);"
            val query_dbversion =
                "CREATE TABLE " + TABLE_DBVERSION + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_VERSION + " INTEGER);"
            val query_deviceId =
                "CREATE TABLE " + TABLE_DEVICEID + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DEVICEID + " VARCHAR);"

            db?.execSQL(query_apprun)
            db?.execSQL(query_server_acesss)
            db?.execSQL(query_dbversion)
            db?.execSQL(query_deviceId)
            if (db != null) {
                Message.message(mContext, "Database Successfully created")
            }
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_APPRUNCOUNT)
            db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_SERVERACCESS)
            db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_DBVERSION)
            db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_DEVICEID)
            onCreate(db)
        }

    }

}
