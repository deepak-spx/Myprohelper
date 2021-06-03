package com.example.myprohelper.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myprohelper.Db.SettingsDatabaseAdapter
import com.example.myprohelper.R
import com.example.myprohelper.SharedPreference.PreferenceData
import com.example.myprohelper.TabFragment.HistoryFragment
import com.example.myprohelper.TabFragment.MakeChangesFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val UPDBCODE1 = "299815553"
    private val UPDBCODE2 = "299975761"
    private val UPDBCODE3 = "299761336"
    private lateinit var settingdatabaseHelper: SettingsDatabaseAdapter
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this@MainActivity
        settingdatabaseHelper = SettingsDatabaseAdapter(this)
        createSettingsDB()
        loadFragment(MakeChangesFragment())
        bott_navigation_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.make_changes -> {
                    loadFragment(MakeChangesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.history -> {
                    loadFragment(HistoryFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun createSettingsDB() {
        settingdatabaseHelper.insertAppRunCount(PreferenceData.getAppRunCount(this)!!.toInt())
        settingdatabaseHelper.insertDbVersion(PreferenceData.getLoginData(this)!!.dBVersion)
        settingdatabaseHelper.insertServerAccess(
            PreferenceData.getLoginData(this)!!.deviceCodeExpiration,
            PreferenceData.getLoginData(this)!!.subDomain,
            PreferenceData.getLoginData(this)!!.deviceGUID,
            UPDBCODE1,
            UPDBCODE2,
            UPDBCODE3,
            PreferenceData.getLoginData(this)!!.companyID.toString()
        )
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }


}