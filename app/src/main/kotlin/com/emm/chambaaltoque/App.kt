package com.emm.chambaaltoque

import android.app.Application
import com.emm.chambaaltoque.core.di.allModules
import com.emm.chambaaltoque.core.di.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                coreModule,
                *allModules,
            )
        }
    }
}