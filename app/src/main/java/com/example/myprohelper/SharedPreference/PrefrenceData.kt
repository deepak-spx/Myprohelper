package com.example.myprohelper.SharedPreference

import android.content.Context
import com.example.myprohelper.App.AppLog
import com.example.myprohelper.App.MyApplication
import com.example.myprohelper.Response.GetSpecifyDeviceCodeResponse
import com.example.myprohelper.utils.Constant
import com.google.gson.Gson

class PreferenceData {

    companion object {

        fun saveLoginData(response: GetSpecifyDeviceCodeResponse) {
            try {
                val sharedPref = MyApplication.instance?.getSharedPreferences(
                    Constant.USER_PREFERENCE,
                    Context.MODE_PRIVATE
                )
                val editor = sharedPref?.edit()
                val gson = Gson()
                val json = gson.toJson(response)
                editor?.putString(Constant.LOGIN_DATA, json)
                editor?.apply()
            } catch (e: Exception) {
                AppLog.showErrorLog(e.message)
            }
        }

        fun getLoginData(context: Context): GetSpecifyDeviceCodeResponse? {
            return try {
                val sharedPref = MyApplication.instance?.getSharedPreferences(
                    Constant.USER_PREFERENCE,
                    Context.MODE_PRIVATE
                )
                val gson = Gson()
                val json = sharedPref?.getString(Constant.LOGIN_DATA, "")
                gson.fromJson(json, GetSpecifyDeviceCodeResponse::class.java)
            } catch (e: Exception) {
                null
            }
        }

        fun setmDeviceUserID(userId: String?) {
            val sharedPref = MyApplication.instance?.getSharedPreferences(
                Constant.USER_ID,
                Context.MODE_PRIVATE
            )
            val editor = sharedPref?.edit()
            editor?.putString(Constant.USER_ID, userId)
            editor?.apply()
        }

        fun getDeviceUserID(context: Context): String? {
            return try {
                val sharedPref =
                    MyApplication.instance?.getSharedPreferences(
                        Constant.USER_ID,
                        Context.MODE_PRIVATE
                    )
                sharedPref?.getString(Constant.USER_ID, "")
            } catch (e: java.lang.Exception) {
                null
            }
        }

        fun setAppRunCount(count: Int) {
            val sharedPref = MyApplication.instance?.getSharedPreferences(
                Constant.APP_RUN_COUNT,
                Context.MODE_PRIVATE
            )
            val editor = sharedPref?.edit()
            editor?.putInt(Constant.APP_RUN_COUNT, count)
            editor?.apply()
        }

        fun getAppRunCount(context: Context): Int? {
            return try {
                val sharedPref = MyApplication.instance?.getSharedPreferences(
                    Constant.APP_RUN_COUNT,
                    Context.MODE_PRIVATE
                )
                sharedPref?.getInt(Constant.APP_RUN_COUNT, 0)
            } catch (e: java.lang.Exception) {
                null
            }
        }


        fun setDbVersion(dbVersion: Int) {
            val sharedPref = MyApplication.instance?.getSharedPreferences(
                Constant.DB_VERSION,
                Context.MODE_PRIVATE
            )
            val editor = sharedPref?.edit()
            editor?.putInt(Constant.DB_VERSION, dbVersion)
            editor?.apply()
        }

        fun getDbVersion(context: Context): Int? {
            return try {
                val sharedPref = MyApplication.instance?.getSharedPreferences(
                    Constant.DB_VERSION,
                    Context.MODE_PRIVATE
                )
                sharedPref?.getInt(Constant.DB_VERSION, 0)
            } catch (e: java.lang.Exception) {
                null
            }
        }


        fun setofflinedbPath(path: String?) {
            val sharedPref = MyApplication.instance?.getSharedPreferences(
                Constant.OFFLINE_PATH,
                Context.MODE_PRIVATE
            )
            val editor = sharedPref?.edit()
            editor?.putString(Constant.OFFLINE_PATH, path)
            editor?.apply()
        }

        fun getofflinedbPath(context: Context): String? {
            return try {
                val sharedPref =
                    MyApplication.instance?.getSharedPreferences(
                        Constant.OFFLINE_PATH,
                        Context.MODE_PRIVATE
                    )
                sharedPref?.getString(Constant.OFFLINE_PATH, "")
            } catch (e: java.lang.Exception) {
                null
            }
        }

        fun setcustomdbPath(path: String?) {
            val sharedPref = MyApplication.instance?.getSharedPreferences(
                Constant.CUSTOM_PATH,
                Context.MODE_PRIVATE
            )
            val editor = sharedPref?.edit()
            editor?.putString(Constant.CUSTOM_PATH, path)
            editor?.apply()
        }

        fun getcustomdbPath(context: Context): String? {
            return try {
                val sharedPref =
                    MyApplication.instance?.getSharedPreferences(
                        Constant.CUSTOM_PATH,
                        Context.MODE_PRIVATE
                    )
                sharedPref?.getString(Constant.CUSTOM_PATH, "")
            } catch (e: java.lang.Exception) {
                null
            }
        }


        fun deleteUserInfoSharedPre(context: Context) {
            val sharedPreferences = MyApplication.instance?.getSharedPreferences(
                Constant.USER_PREFERENCE,
                Context.MODE_PRIVATE
            )
            val sharedUserId =
                MyApplication.instance?.getSharedPreferences(Constant.USER_ID, Context.MODE_PRIVATE)
            val sharedAppRunCount = MyApplication.instance?.getSharedPreferences(
                Constant.APP_RUN_COUNT,
                Context.MODE_PRIVATE
            )
            val sharedOfflinePath = MyApplication.instance?.getSharedPreferences(
                Constant.OFFLINE_PATH,
                Context.MODE_PRIVATE
            )
            val sharedCustomPath = MyApplication.instance?.getSharedPreferences(
                Constant.CUSTOM_PATH,
                Context.MODE_PRIVATE
            )
            val sharedDbVersion = MyApplication.instance?.getSharedPreferences(
                            Constant.DB_VERSION,
                            Context.MODE_PRIVATE
                        )

            sharedPreferences?.edit()?.clear()?.apply()
            sharedUserId?.edit()?.clear()?.apply()
            sharedAppRunCount?.edit()?.clear()?.apply()
            sharedOfflinePath?.edit()?.clear()?.apply()
            sharedCustomPath?.edit()?.clear()?.apply()
            sharedDbVersion?.edit()?.clear()?.apply()

        }
    }

}
