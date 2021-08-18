package com.example.wonhoi_moive_review_service_app

import android.app.Application
import com.example.wonhoi_moive_review_service_app.di.appModule
import com.example.wonhoi_moive_review_service_app.di.dataModule
import com.example.wonhoi_moive_review_service_app.di.domainModule
import com.example.wonhoi_moive_review_service_app.di.presenterModule
import com.google.firebase.ktx.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieReviewApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(
                if (BuildConfig.DEBUG) {
                    Level.DEBUG
                } else {
                    Level.NONE
                }
            )
            androidContext(this@MovieReviewApplication)
            modules(appModule + dataModule + domainModule + presenterModule)
        }
    }
}