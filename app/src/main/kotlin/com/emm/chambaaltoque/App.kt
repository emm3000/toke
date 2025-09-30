package com.emm.chambaaltoque

import android.app.Application
import com.emm.chambaaltoque.auth.authModule
import com.emm.chambaaltoque.core.di.coreModule
import com.emm.chambaaltoque.home.homeModule
import com.emm.chambaaltoque.postjob.postJobModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(
                coreModule,
                authModule,
                postJobModule,
                homeModule,
            )
        }
    }
}