package com.example.myprohelper.data.repositories

import com.example.myprohelper.Response.GetSpecifyDeviceCodeResponse
import com.example.myprohelper.data.network.SafeApiRequest
import com.example.myprohelper.data.network.ServiceApi
import okhttp3.ResponseBody
import retrofit2.http.Header
import retrofit2.http.Url

class DBRepositories (
    private val api: ServiceApi
) : SafeApiRequest() {


    suspend fun getDBDetail(id:Int): GetSpecifyDeviceCodeResponse {
        return apiRequest { api.GetSpecificByDeviceCode(id) }
    }

    suspend fun getDownloadDB(url: String,UpDnCode: String, DeviceCode: String): ResponseBody {
        return apiRequest { api.download(url,UpDnCode, DeviceCode) }
    }

}