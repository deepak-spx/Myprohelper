package com.example.myprohelper.TabFragment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myprohelper.ui.WelcomeActivity
import com.example.myprohelper.Classes.DBHelper
import com.example.myprohelper.Classes.MyprohelperServer
import com.example.myprohelper.Db.DataBaseHelperClass
import com.example.myprohelper.Db.MyProhelperDatabaseAdapter
import com.example.myprohelper.Db.SettingsDatabaseAdapter
import com.example.myprohelper.R
import com.example.myprohelper.Service.RetrofitClient
import com.example.myprohelper.SharedPreference.PreferenceData
import com.example.myprohelper.ui.MainActivity
import com.example.myprohelper.ui.load.LoadViewModel
import com.example.myprohelper.ui.load.LoadViewModelFactory
import com.example.myprohelper.utils.*
import kotlinx.android.synthetic.main.fragment_make_changes.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


class MakeChangesFragment() : Fragment(), View.OnClickListener {

    private lateinit var tv_db_changes_count: TextView
    private lateinit var tv_db_version: TextView
    private lateinit var tv_last_server_update: TextView
    private lateinit var et_update1: TextView
    private lateinit var et_update2: TextView
    private lateinit var start_over: TextView
    private lateinit var check_update: TextView


    private lateinit var settingdatabaseHelper: SettingsDatabaseAdapter
    private lateinit var myProhelperDatabaseAdapter: MyProhelperDatabaseAdapter
    private lateinit var dataBaseHelperClass: DataBaseHelperClass
    var apprunCount = 0

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "MyProhelper_Notify"
    private val description = "Test notification"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_make_changes, container, false)
        settingdatabaseHelper = SettingsDatabaseAdapter(activity!!)
        myProhelperDatabaseAdapter = MyProhelperDatabaseAdapter(activity!!)
        dataBaseHelperClass = DataBaseHelperClass(activity!!)


        notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

        tv_db_changes_count = rootView.findViewById(R.id.tv_db_changes_count)
        tv_db_version = rootView.findViewById(R.id.tv_db_version)
        tv_last_server_update = rootView.findViewById<TextView>(R.id.tv_last_server_update)
        et_update1 = rootView.findViewById<TextView>(R.id.et_update1)
        et_update2 = rootView.findViewById<TextView>(R.id.et_update2)
        start_over = rootView.findViewById<TextView>(R.id.start_over)
        check_update = rootView.findViewById<TextView>(R.id.check_update)
        et_update1.setOnClickListener(this)
        et_update2.setOnClickListener(this)
        start_over.setOnClickListener(this)
        check_update.setOnClickListener(this)

        return rootView
    }

    private fun changesdb() {
        download()
        dataBaseHelperClass!!.open()
        var id = dataBaseHelperClass.versiondb()
        dataBaseHelperClass!!.close()
        tv_db_version.setText(""+id)
        tv_db_changes_count.setText(""+PreferenceData.getAppRunCount(activity!!))

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_update1 -> {
                Constant.btn_last_click(activity!!)
                val dbTest = DatabaseTest1()
                download()
                dataBaseHelperClass!!.open()
                tv_last_server_update.setText(Constant.currentDate() + " at " + getCurrentTime())
                apprunCount = PreferenceData.getAppRunCount(activity!!)!!
                apprunCount++;
                PreferenceData.setAppRunCount(apprunCount);
                tv_db_changes_count.setText("" + apprunCount)
                dataBaseHelperClass!!.open()
                dataBaseHelperClass.insertAssets()

                var id = dataBaseHelperClass.versiondb()
                dataBaseHelperClass!!.close()

                Log.e("@dbvers", id.toString())
                tv_db_version.setText("" + id)
                showNotificationMessage("Update1")
            }

            R.id.et_update2 -> {
                Constant.btn_last_click(activity!!)
                //download()
                val dbTest = DatabaseTest1()
                //dbTest.try202()
                tv_last_server_update.setText(Constant.currentDate() + " at " + getCurrentTime())
                showNotificationMessage("Update2")
                dataBaseHelperClass!!.open()
                var id = dataBaseHelperClass.versiondb()
                dataBaseHelperClass!!.close()
                Log.e("@dbvers", id.toString())
                tv_db_version.setText("" + id)
            }

            R.id.start_over -> {
                Constant.btn_last_click(activity!!)
                //   removeCompanyDatabase()
                PreferenceData.deleteUserInfoSharedPre(activity!!)
                apprunCount = 0
                PreferenceData.setAppRunCount(apprunCount);
                tv_db_changes_count.setText("" + apprunCount)
                tv_db_version.setText("0")
                settingdatabaseHelper.insertAppRunCount(apprunCount)
                myProhelperDatabaseAdapter.insertData(
                    apprunCount.toString(),
                    "View Change",
                    "Download DB failed switching to InitialController view"
                )
                showNotificationMessage("Start Over")
            }

            R.id.check_update -> {
                Constant.btn_last_click(activity!!)
                download()
                var server = MyprohelperServer()
                //server.getDBChanges(activity!!)
                tv_last_server_update.setText(Constant.currentDate() + " at " + getCurrentTime())
                dataBaseHelperClass!!.open()
                var id = dataBaseHelperClass.versiondb()
                PreferenceData.setDbVersion(id!!.toInt())
                dataBaseHelperClass!!.close()
                Log.e("@dbvers", id.toString())
                tv_db_version.setText("" + id)
                showNotificationMessage("Check Update")
            }

        }
    }

    @Suppress("DEPRECATION")
    private fun showNotificationMessage(s: String) {
        val intent = Intent(activity, WelcomeActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val contentView = RemoteViews("com.example.myprohelper", R.layout.notification_collapsed)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(activity, channelId)
                .setContent(contentView)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(s)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources,
                        R.drawable.ic_launcher_background
                    )
                )
                .setContentIntent(pendingIntent)
        } else {

            builder = Notification.Builder(activity)
                .setContent(contentView)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(s)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources,
                        R.drawable.ic_launcher_background
                    )
                )
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }

    fun getCurrentTime(): String {
        val calendar: Calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat("HH:mm:ss")
        val strDate = mdformat.format(calendar.getTime())
        return strDate
    }

    fun download() {
        var DbUrl = "https://199.127.60.116:5005/api/DownloadDB"
        RetrofitClient.instance.download(DbUrl, "299827204", "616030")
            ?.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    CoroutineScope(Dispatchers.IO).launch {
                      async {  writeResponseBodyToDisk(response.body()!!) } .await()
                    }


                    Log.e(ContentValues.TAG, "onResponse: " + response.toString())
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Log.e(ContentValues.TAG, "onResponse: " + t.toString())
                }
            })

    }

    private fun writeResponseBodyToDisk(body: ResponseBody): String {
        val fileName = DBHelper.getChangesDBDocName() + DBHelper.getChangesDBDocNameExtension()
        //   val file = File(context.filesDir, fileName)

        var input: InputStream? = null
        try {
            val file = File(activity!!.getDatabasePath(fileName).getAbsolutePath())
            PreferenceData.setofflinedbPath(activity!!.filesDir.toString())
            if (body == null)
                return ""

            input = body.byteStream()
            var fos = FileOutputStream(file)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            Log.e("saveFile",file.toString())
            return file.toString()
        } catch (e: Exception) {
            Log.e("saveFile", e.toString())
        } finally {
            input?.close()
        }
        return ""
    }

    override fun onResume() {
        super.onResume()
        changesdb()
    }


}

