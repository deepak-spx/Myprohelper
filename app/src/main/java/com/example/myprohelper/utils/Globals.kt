package com.example.myprohelper.utils

class Globals {
    var serverAccessCode = ""
    var companyID =""
    val UPDBCODE1 ="299815553"
    val UPDBCODE2 ="299975761"
    val UPDBCODE3 ="299761336"

    fun getofflineDBFullFileName(): String {

        var nameForFile = "offline"
        var extForFile = ".db"

//        var documentsURL = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first
//        var destURL = documentsURL!.appendingPathComponent(nameForFile).appendingPathExtension(extForFile)
//        return destURL

        return nameForFile+extForFile
    }

   fun getofflineDBFullFileNameString(): String {
        var url = getofflineDBFullFileName();
        return url.toString()


    }

    fun getCompanyDBNameNoPath(): String {
        return companyID+(".db")
    }

}