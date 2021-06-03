package com.example.myprohelper.Response


import com.google.gson.annotations.SerializedName

data class GetSpecifyDeviceCodeResponse(
    @SerializedName("CompanyID")
    val companyID: Int,
    @SerializedName("DBVersion")
    val dBVersion: String,
    @SerializedName("DateCreated")
    val dateCreated: String,
    @SerializedName("DateModified")
    val dateModified: String,
    @SerializedName("DeviceCode")
    val deviceCode: String,
    @SerializedName("DeviceCodeExpiration")
    val deviceCodeExpiration: String,
    @SerializedName("DeviceGUID")
    val deviceGUID: String,
    @SerializedName("DeviceID")
    val deviceID: Int,
    @SerializedName("IsDeviceSetup")
    val isDeviceSetup: Boolean,
    @SerializedName("PushNotificationID")
    val pushNotificationID: String,
    @SerializedName("Removed")
    val removed: Boolean,
    @SerializedName("RemovedDate")
    val removedDate: String,
    @SerializedName("SampleDevice")
    val sampleDevice: Boolean,
    @SerializedName("SubDomain")
    val subDomain: String,
    @SerializedName("WorkerID")
    val workerID: Int
)