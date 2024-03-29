package com.example.newsportal.di

import com.example.newsportal.view.newslist.MainActivity
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    PresenterModule::class])

interface AppComponent{

    fun inject(activity: MainActivity)
}