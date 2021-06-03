package com.example.myprohelper.utils

import android.content.Context
import android.util.Log
import com.example.myprohelper.Db.MyProhelperDatabaseAdapter
import com.example.myprohelper.Db.SettingsDatabaseAdapter
import com.example.myprohelper.SharedPreference.PreferenceData
import java.lang.Exception

class DatabaseTest1 {

    lateinit var context:Context
    private lateinit var settingdatabaseHelper: SettingsDatabaseAdapter
    private lateinit var myProhelperDatabaseAdapter: MyProhelperDatabaseAdapter

    fun test1() {
     //   var offline = DBHelper.getChangesDBURL()

        var offlineStr  =  PreferenceData.getofflinedbPath(context)
        var custDBStr  =  PreferenceData.getcustomdbPath(context)

        print("offline - (offlineStr)")
        print("custDBStr - (custDBStr)")
        try {
            if (!custDBStr!!.isEmpty()) {    //if check path of customm db
                print("custDBStr exists")
            }
          } catch (e: Exception) {
        }


    }

    fun try202() {
       val globals = Globals()
       var companyID  = globals.companyID
        settingdatabaseHelper = SettingsDatabaseAdapter(context)
        myProhelperDatabaseAdapter = MyProhelperDatabaseAdapter(context)

//       var company = CompanyID(id: companyID)
//       var custDB = company.getDBFileNameWithPath()
//
//       var offline = DBHelper.getChangesDBURL()

           var offlineStr  =  PreferenceData.getofflinedbPath(context)
           var custDBStr  =  PreferenceData.getcustomdbPath(context)

           if (custDBStr!=null)
           Log.e("custom db exist ",custDBStr)
            }
        var curTime = Constant.currentDate()+" at "+Constant.getCurrentTime()
//    "Update chg.[Assets] set Description = '\(curTime)'  where AssetID = \(DatabaseTest1.insertedRowTestID)"
//        print("try202 - \(updateSQL)")


}