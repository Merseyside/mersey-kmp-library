package com.merseyside.sample

import com.merseyside.archy.BaseApplication
import com.merseyside.sample.di.androidModule
import com.merseyside.sample.di.initKoin
import org.koin.android.ext.koin.androidContext

class MainApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        val koin = initKoin(androidModule) {
            androidContext(this@MainApplication)
        }.koin
    }
}