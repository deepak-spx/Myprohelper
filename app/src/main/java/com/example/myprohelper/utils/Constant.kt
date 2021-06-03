package com.example.myprohelper.utils

import android.R
import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.os.Build
import android.os.Environment
import android.os.SystemClock
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.myprohelper.ui.WelcomeActivity
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


class Constant {
    companion object {

        val USER_PREFERENCE = "MyprohelperSample-Android"
        val LOGIN_DATA = "login_data"
        val APP_NAME = "MyprohelperSample"
        val USER_ID = "USERID"
        val APP_RUN_COUNT = "AppRunCount"
        val OFFLINE_PATH = "offline_db"
        val CUSTOM_PATH = "custom_db"
        val DB_VERSION = "db_Version"
        var mLastClickTime: Long = 0
        var DbVersion = 1

        fun btn_last_click(context: Context) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime()
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        }

        fun hideSoftKeyBoard(context: Context, view: View) {
            try {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            } catch (e: Exception) {
                // TODO: handle exception
                e.printStackTrace()
            }

        }

        fun currentDate(): String {
            val MILLIS_IN_DAY = 1000 * 60 * 60 * 24
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val currDate: String = dateFormat.format(date.time)
            return getMonth(currDate)
        }

        fun getMonth(dob: String?): String {
            var sdf = SimpleDateFormat("yyyy-MM-dd")
            val date = sdf.parse(dob)
            sdf = SimpleDateFormat("dd MMM, yyyy")
            val yourFormatedDateString = sdf.format(date)
            return yourFormatedDateString
        }

        fun getCurrentTime(): String {
            val calendar: Calendar = Calendar.getInstance()
            val mdformat = SimpleDateFormat("HH:mm:ss")
            val strDate = mdformat.format(calendar.getTime())
            return strDate
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getDeviceUniqueId(context: Context): String {
            var id = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
            );
            return id.toString()
        }

        fun onErrorDialog(context: Context) {
            val builder =
                AlertDialog.Builder(context)
            builder.setTitle(getBoldText("Connection Failed"))
            builder.setMessage("Please ensure your device has internet connection.")
            val positiveText = context.getString(R.string.ok)
            builder.setPositiveButton(
                positiveText
            ) { dialog, which -> dialog.dismiss() }
            val dialog = builder.create()
            // display dialog
            dialog.show()
        }

        fun okDialog(
            msg: String?,
            title: String?,
            context: Context
        ) {
            val builder =
                AlertDialog.Builder(context)
            builder.setTitle(title?.let { getBoldText(it) })
            builder.setMessage(msg)
            val positiveText = context.getString(R.string.ok)
            builder.setPositiveButton(
                positiveText
            ) { dialog, which -> dialog.dismiss() }
            val dialog = builder.create()
            // display dialog
            dialog.show()
        }

        fun getBoldText(title: String): SpannableStringBuilder? {
            val sb = SpannableStringBuilder(title)
            val bold_title = StyleSpan(Typeface.BOLD)
            sb.setSpan(bold_title, 0, title.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            return sb
        }

        fun onError(mActivity: Activity, @StringRes resId: Int) {
            try {
                onError(mActivity, mActivity.getString(resId))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun showSnackBar(mActivity: Activity, message: String) {
            val snackbar = Snackbar.make(
                mActivity.findViewById(R.id.content),
                message, Snackbar.LENGTH_SHORT
            )
            val sbView = snackbar.view
            val textView = sbView
                .findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(ContextCompat.getColor(mActivity, R.color.holo_green_light))
            snackbar.show()
        }

        fun onError(mActivity: Activity, message: String) {
            if (message != null) {
                showSnackBar(mActivity, message)
            } else {
                showSnackBar(mActivity, "")
            }
        }

        val isExternalStorageReadOnly: Boolean
            get() {
                val extStorageState = Environment.getExternalStorageState()
                return if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
                    true
                } else {
                    false
                }
            }
        val isExternalStorageAvailable: Boolean
            get() {
                val extStorageState = Environment.getExternalStorageState()
                return if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
                    true
                } else {
                    false
                }
            }




        fun showNotification(context: Context, title: String, message: String) {
            val intent = Intent(context, WelcomeActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("msg", message)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            //Create Notification using NotificationCompat.Builder
            val builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.sym_def_app_icon)
                .setContentTitle("MyProhelper")
                .setContentText("MyProhelper App")
                .addAction(R.drawable.arrow_down_float, "Action Button", pendingIntent)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            // Create Notification Manager
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            // Build Notification with Notification Manager
            notificationManager.notify(0, builder.build())
        }
    }


}