package com.example.newsportal.di

import com.example.newsportal.view.newslist.MainActivity
import dagger.Component
import dagger.Module


@Component(modules = [PresenterModule::class])
interface MainComponent {
    fun inject(activity: MainActivity)

}
