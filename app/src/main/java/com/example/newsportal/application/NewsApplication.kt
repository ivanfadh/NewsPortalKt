package com.example.newsportal.application

import android.app.Application
import com.example.newsportal.di.AppComponent
import com.example.newsportal.di.AppModule
import com.example.newsportal.di.DaggerAppComponent

class NewsApplication : Application() {
    lateinit var newsComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        newsComponent = initDagger(this)
    }

    private fun initDagger(app: NewsApplication): AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(app))
                    .build()

}
