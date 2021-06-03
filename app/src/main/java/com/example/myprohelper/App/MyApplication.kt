package com.example.myprohelper.App

import android.app.Application
import com.example.myprohelper.data.network.NetworkConnectionInterceptor
import com.example.myprohelper.data.network.ServiceApi
import com.example.myprohelper.data.repositories.DBRepositories
import com.example.myprohelper.ui.load.LoadViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MyApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ServiceApi(instance()) }
        bind() from singleton { DBRepositories(instance()) }
        bind() from singleton { LoadViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        myInstance = this
    }

    companion object {
        private var myInstance: MyApplication? = null

        @get:Synchronized
        val instance: MyApplication?
            get() {
                if (myInstance == null) myInstance =
                    MyApplication()
                return myInstance
            }
    }


}