package com.example.myprohelper.data.network

import com.example.myprohelper.Response.GetSpecifyDeviceCodeResponse
import com.example.myprohelper.Service.RetrofitClient
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

interface ServiceApi {

    @Headers("Content-Type: application/json")
    @GET("/ga/Devices/GetSpecificByDeviceCode")
    suspend fun GetSpecificByDeviceCode(
        @Query("DeviceCode") DeviceCode: Int
    ): Response<GetSpecifyDeviceCodeResponse>

    @Headers("Content-Type: application/json")
    @GET()
    suspend fun download(
        @Url url: String,
        @Header("UpDnCode") UpDnCode: String,
        @Header("DeviceCode") DeviceCode: String
    ): Response<ResponseBody>


    companion object{
        private const val BASE_URL = "https://myprohelper.com:5005"

        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : ServiceApi{

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()



            return Retrofit.Builder()
                .client(getUnsafeOkHttpClient(networkConnectionInterceptor, loggingInterceptor))
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServiceApi::class.java)
        }


        fun getUnsafeOkHttpClient(networkConnectionInterceptor: NetworkConnectionInterceptor,loggingInterceptor: HttpLoggingInterceptor): OkHttpClient? {
            return try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate?>?,
                            authType: String?
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate?>?,
                            authType: String?
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                            return arrayOf()
                        }
                    }
                )

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory
                val trustManagerFactory: TrustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(null as KeyStore?)
                val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
                check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                    "Unexpected default trust managers:" + trustManagers.contentToString()
                }
                val trustManager = trustManagers[0] as X509TrustManager
                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustManager)
                builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
                builder.readTimeout(60, TimeUnit.SECONDS)
                builder .connectTimeout(60, TimeUnit.SECONDS)
                builder.writeTimeout(60, TimeUnit.SECONDS)
                builder .addInterceptor(networkConnectionInterceptor)
                builder .addInterceptor(loggingInterceptor)
                builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }



}