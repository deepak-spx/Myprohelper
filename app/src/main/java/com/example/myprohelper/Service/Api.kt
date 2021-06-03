package com.example.myprohelper.Service

import com.example.myprohelper.Response.GetSpecifyDeviceCodeResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface Api {

    @Headers("Content-Type: application/json")
    @GET("/ga/Devices/GetSpecificByDeviceCode")
    fun GetSpecificByDeviceCode(
        @Query("DeviceCode") DeviceCode: Int
    ): Call<GetSpecifyDeviceCodeResponse>

    @Headers("Content-Type: application/json")
    @PUT("/api/DBChanges")
    fun GetDbChanges(
        @Header("UpDnCode") UpDnCode: String,
        @Header("DBHash") DBHash: String,
    ): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @GET()
    fun download(
        @Url url: String,
        @Header("UpDnCode") UpDnCode: String,
        @Header("DeviceCode") DeviceCode: String
    ): Call<ResponseBody>

}