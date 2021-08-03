package com.example.scanitdemo

import android.app.Application
import com.example.scanitdemo.koin.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


/**
 * Created by Ara Hakobyan on 05.06.21.
 * Preezma
 */
class ScanItApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@ScanItApplication)
            modules(AppModule.modules)
        }
    }
}