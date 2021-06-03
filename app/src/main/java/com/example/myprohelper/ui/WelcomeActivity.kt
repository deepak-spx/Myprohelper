package com.example.myprohelper.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myprohelper.R
import com.example.myprohelper.SharedPreference.PreferenceData
import com.example.myprohelper.ui.load.LoadingActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {
    private var mLastClickTime: Long = 0
    var apprunCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        et_submit.setOnClickListener(this)
        /*apprunCount = PreferenceData.getAppRunCount(this)!!;
        apprunCount++;
        PreferenceData.setAppRunCount(apprunCount);*/
        checkPermissions()
        CoroutineScope(Dispatchers.IO).launch {
            if (PreferenceData.getLoginData(this@WelcomeActivity) != null) {
                val i = Intent(this@WelcomeActivity, MainActivity::class.java)
                startActivity(i)
            }
        }

    }

    override fun onStart() {

        super.onStart()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_submit -> {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime()
                var access_code = et_code.text.toString()
                if (access_code!= ""){
                    val i = Intent(this@WelcomeActivity, LoadingActivity::class.java)
                    i.putExtra("@code_fill", access_code)
                    startActivity(i)
                }else{
                    Toast.makeText(
                        applicationContext,
                        "Please Enter Access Code",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }
    }

    fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                applicationContext, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //Do_SOme_Operation();
        } else {
            requestStoragePermission()
        }
    }

    fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
            1234
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1234 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // Do_SOme_Operation();
                }
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }


}