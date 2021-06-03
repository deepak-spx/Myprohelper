package com.example.myprohelper.App

import android.util.Log
import com.example.myprohelper.BuildConfig
import com.example.myprohelper.utils.Constant

object AppLog {

    fun showLog(message: String?) {
        if (BuildConfig.DEBUG) {
            Log.i(Constant.APP_NAME, message!!)
        }
    }

    fun showErrorLog(message: String?) {
        if (BuildConfig.DEBUG) {
            Log.e(Constant.APP_NAME, message!!)
        }
    }
}
