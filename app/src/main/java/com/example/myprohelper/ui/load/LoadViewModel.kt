package com.example.myprohelper.ui.load

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprohelper.Response.GetSpecifyDeviceCodeResponse
import com.example.myprohelper.data.repositories.DBRepositories
import com.example.myprohelper.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class LoadViewModel (
    var repository: DBRepositories
): ViewModel(){

    private val dbInformation = MutableLiveData<Resource<GetSpecifyDeviceCodeResponse?>>()
    private val dbDownload = MutableLiveData<Resource<ResponseBody?>>()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        dbInformation.postValue(Resource.error(exception.message.toString(), null))
        dbDownload.postValue(Resource.error(exception.message.toString(), null))
    }

    init {
        fetchDBDetail()
    }

    private fun fetchDBDetail() {
        viewModelScope.launch(exceptionHandler) {
            dbInformation.postValue(Resource.loading(null))
            val dbInfo = repository.getDBDetail(616030)
            dbInformation.postValue(Resource.success(dbInfo))
        }
    }

    fun getDBDetail(): LiveData<Resource<GetSpecifyDeviceCodeResponse ?>> {
        return dbInformation
    }

    fun getDbDownload(url: String,UpDnCode: String, DeviceCode: String): MutableLiveData<Resource<ResponseBody?>> {
        viewModelScope.launch(exceptionHandler) {
            dbDownload.postValue(Resource.loading(null))
            val dbInfo = repository.getDownloadDB(url,UpDnCode, DeviceCode)
            dbDownload.postValue(Resource.success(dbInfo))
        }

        return dbDownload
    }

}