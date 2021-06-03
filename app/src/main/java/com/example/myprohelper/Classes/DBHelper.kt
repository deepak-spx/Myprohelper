package com.example.myprohelper.Classes

import android.content.Context
import com.example.myprohelper.Db.MyProhelperDatabaseAdapter
import com.example.myprohelper.SharedPreference.PreferenceData
import com.example.myprohelper.utils.Globals

class DBHelper {
    companion object {
        var context: Context? = null
        var myProhelperDatabaseAdapter: MyProhelperDatabaseAdapter? = null

        fun getDatabaseChanges(): Int {
            myProhelperDatabaseAdapter = MyProhelperDatabaseAdapter(context!!)
            var maxDBVersionChangeID = 0
            var chgs = ""
            val globals = Globals()
            var offlineDBFilename = globals.getofflineDBFullFileName()
            var maxchenges = myProhelperDatabaseAdapter!!.getAllData()[0].id
            return (maxchenges)
        }

        fun getDBVersion(): Int {
            var dbFilename = ""
            var id1 = 0

            if (dbFilename == getChangesDBFilename()) {
            }

            return id1

        }

        fun getChangesDBURL(): String {
            var documentsURL = context!!.filesDir //existing db path
            var destURL = context!!.filesDir//new path of db
            return destURL.toString()
        }

        fun getChangesDBDocName(): String {
            return "offline"
        }

        fun getChangesDBDocNameExtension(): String {
            return ".db"
        }

        fun getChangesDBCount(): Int {
            var changeCount = 0

            var dbFilename = getChangesDBFilename()
            try {
//            let dbQueue = try DatabaseQueue(path: dbFilename)
//                try dbQueue.read {
//                    db /* -> Int?  */ in
//                            let count = try Int.fetchOne(db, sql:"select count(*) from DBVersionChanges")
//                    if let count1 = count {
//                        changeCount = count1
//                    }
//                }
            } catch (e: Exception) {
                print("dbHelper.getChangeDBCount: " + e.toString())
            }
            return changeCount

        }

        fun getChangesDBFilename(): String {
            var url = getChangesDBURL()
            return url
        }


        fun removeCompanyDatabase() {
            context!!.deleteDatabase("myprohelperSample_db")
            context!!.deleteDatabase("Settings.db")
            PreferenceData.deleteUserInfoSharedPre(context!!)
//            val i = Intent(context, WelcomeActivity::class.java)
//            context.startActivity(i)
        }

        fun emptyChangesDB() {

            var didCreateEmptyChanges: Boolean = false
            var changeDBURL = getChangesDBURL()

            var dbFilename = getChangesDBFilename()
            print("dbFileName: (dbFilename)")


        }
    }

}