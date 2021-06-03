package com.example.myprohelper.Classes

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.myprohelper.R
import com.example.myprohelper.Service.RetrofitClient
import com.example.myprohelper.utils.Constant
import com.example.myprohelper.utils.Globals
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyprohelperServer {
    lateinit var context: Context

    fun getDBChanges(activity: FragmentActivity) {
        var UPDBCode = ""
        var dbVersionBeforeRequest = DBHelper.getDBVersion()
        var rr = DBHelper.getDatabaseChanges()
        var changeCountBeforeRequest = DBHelper.getChangesDBCount()
        var dbVersion = DBHelper.getDBVersion()
        val globals = Globals()
        if (UPDBCode.equals(globals.UPDBCODE1)) {
            dbchanges(activity)
        } else {
            print("MyProHelperServer.getDBChanges couldn't  get serverAccessCode?.UPDBCode1")
        }
    }

    fun dbchanges(activity: FragmentActivity) {
        val globals = Globals()
        RetrofitClient.instance.GetDbChanges(globals.UPDBCODE1, "")
            ?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    responseMy: Response<ResponseBody>
                ) {
                    try {
                        if (responseMy.isSuccessful) {
                            Log.e("@response", responseMy.body().toString())
                        } else {
                            val gson = GsonBuilder().create()
                            val myError: Response<ResponseBody>? = gson.fromJson(
                                responseMy.errorBody()!!.string(),
                                responseMy::class.java
                            )
                            Log.e(ContentValues.TAG, "onResponse: " + myError?.message())
                        }
                    } catch (e: Exception) {
                        Log.e(ContentValues.TAG, "onResponse: " + e.toString())
                        activity?.let {
                            Constant.onError(it, R.string.retry)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(ContentValues.TAG, "onResponse: " + t.toString())
                    activity?.let { Constant.onError(it, R.string.retry) }
                }
            })
    }


}