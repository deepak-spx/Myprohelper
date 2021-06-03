package com.example.myprohelper.ui.load

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myprohelper.App.AppLog
import com.example.myprohelper.Classes.DBHelper
import com.example.myprohelper.Db.MyProhelperDatabaseAdapter
import com.example.myprohelper.R
import com.example.myprohelper.Response.GetSpecifyDeviceCodeResponse
import com.example.myprohelper.Service.RetrofitClient
import com.example.myprohelper.SharedPreference.PreferenceData
import com.example.myprohelper.databinding.ActivityLoadingBinding
import com.example.myprohelper.ui.MainActivity
import com.example.myprohelper.utils.Constant
import com.example.myprohelper.utils.Status
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_loading.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*


class LoadingActivity : AppCompatActivity(), KodeinAware {
    private lateinit var binding: ActivityLoadingBinding
    override val kodein by kodein()
    val TAG: String? = "@Myprohelper"
    val SPLASH_TIME_OUT = 3000L
    internal var myExternalFile: File? = null
    private lateinit var databaseHelper: MyProhelperDatabaseAdapter
    lateinit var context: Context
    var accessCode = ""

    private val factory: LoadViewModelFactory by instance()

    private lateinit var viewModel: LoadViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loading)
        viewModel = ViewModelProvider(this, factory).get(LoadViewModel::class.java)

        context = this@LoadingActivity
        databaseHelper = MyProhelperDatabaseAdapter(this)
        var deviceId = 616030
        val intent = getIntent()
        accessCode = intent.getStringExtra("@code_fill")!!
        // getSpecificByDeviceCode(accessCode.toInt())
        setupObserver()

    }

    /*private fun getSpecificByDeviceCode(accessCode: Int) {
        prog_loading.visibility = View.VISIBLE
        RetrofitClient.instance.GetSpecificByDeviceCode(accessCode)
            ?.enqueue(object : Callback<GetSpecifyDeviceCodeResponse> {
                override fun onResponse(
                    call: Call<GetSpecifyDeviceCodeResponse>,
                    responseMy: Response<GetSpecifyDeviceCodeResponse>
                ) {
                    prog_loading.visibility = View.GONE
                    try {
                        if (responseMy.isSuccessful) {
                            val defaultResponse = responseMy.body()
                            Log.e("@response", defaultResponse.toString());

                            download()
                            *//*Handler().postDelayed(
                                {
                                    val i = Intent(
                                        this@LoadingActivity,
                                        MainActivity::class.java
                                    )
                                    startActivity(i)
                                    finish()
                                }, SPLASH_TIME_OUT
                            )*//*
                        } else {
                            val gson = GsonBuilder().create()
                            val myError: Response<GetSpecifyDeviceCodeResponse>? = gson.fromJson(
                                responseMy.errorBody()!!.string(),
                                responseMy::class.java
                            )
                            Log.e(ContentValues.TAG, "onResponse: " + myError?.message())
                            this@LoadingActivity?.let {
                                Constant.okDialog(myError?.message(), getString(R.string.error), it)
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(ContentValues.TAG, "onResponse: " + e.toString())
                        this@LoadingActivity?.let {
                            Constant.onError(it, R.string.retry)
                        }
                    }
                }

                override fun onFailure(call: Call<GetSpecifyDeviceCodeResponse>, t: Throwable) {
                    prog_loading.visibility = View.GONE
                    Log.e(ContentValues.TAG, "onResponse: " + t.toString())
                    this@LoadingActivity?.let { Constant.onError(it, R.string.retry) }
                }
            })
    }*/

    private fun loadStaffDataToPreference(data: GetSpecifyDeviceCodeResponse) {
        try {
            PreferenceData.saveLoginData(data)
        } catch (e: Exception) {
            AppLog.showErrorLog(e.toString())
        }
    }

    fun download() {
        var DbUrl = "https://199.127.60.116:5005/api/DownloadDB"
        viewModel.getDbDownload(DbUrl, "299827204", "616030")
            .observe(this, androidx.lifecycle.Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        Log.i("Api Response ", it.data.toString())
                        it.data?.let { responseBody ->

                            CoroutineScope(Dispatchers.IO).launch {
                                async {
                                    if (!writeResponseBodyToDisk(responseBody!!).isNullOrEmpty()) {
                                        startActivity(
                                            Intent(
                                                this@LoadingActivity,
                                                 MainActivity::class.java
                                            )
                                        )
                                    }
                                }.await()
                            }


                        }
                    }
                    Status.LOADING -> {
                        Log.i("Api Response ", it.status.toString())

                    }
                    Status.ERROR -> {
                        Log.i("Api Response ", it.message.toString())

                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            })


        /*   RetrofitClient.instance.download(DbUrl, )
               ?.enqueue(object : Callback<ResponseBody?> {
                   override fun onResponse(
                       call: Call<ResponseBody?>,
                       response: Response<ResponseBody?>
                   ) {
                       if(!writeResponseBodyToDisk(response.body()!!).isNullOrEmpty()){
                           startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
                       }
                       Log.e(ContentValues.TAG, "onResponse: " + response.toString())
                   }

                   override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                       Log.e(ContentValues.TAG, "onResponse: " + t.toString())
                   }
               })*/
    }

    private fun writeResponseBodyToDisk(body: ResponseBody): String {
        //val fileName="offline.db"
        val fileName = DBHelper.getChangesDBDocName() + DBHelper.getChangesDBDocNameExtension()
        //   val file = File(context.filesDir, fileName)
        val file = File(context.getDatabasePath(fileName).getAbsolutePath())
        PreferenceData.setofflinedbPath(context.filesDir.toString())
        if (body == null)
            return ""
        var input: InputStream? = null
        try {
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
            Log.e("saveFile", file.toString())
            return file.toString()
        } catch (e: Exception) {
            Log.e("saveFile", e.toString())
        } finally {
            input?.close()
        }
        return ""
    }


    private fun setupObserver() {
        viewModel.getDBDetail().observe(this, {

            when (it.status) {
                Status.SUCCESS -> {
                    Log.i("Api Response ", it.data.toString())
                    it.data?.let { getSpecificByDeviceCode ->
                        loadStaffDataToPreference(getSpecificByDeviceCode!!)
                        databaseHelper.insertData(
                            getSpecificByDeviceCode.dBVersion,
                            getSpecificByDeviceCode.deviceCodeExpiration,
                            getSpecificByDeviceCode.removedDate
                        )
                        download()
                    }
                }
                Status.LOADING -> {
                    Log.i("Api Response ", it.status.toString())

                }
                Status.ERROR -> {
                    Log.i("Api Response ", it.message.toString())

                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()

    }

}


