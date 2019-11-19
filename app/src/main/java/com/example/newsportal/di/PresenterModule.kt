package com.example.newsportal.di

import com.example.newsportal.view.newslist.NewsMVPContract
import com.example.newsportal.view.newslist.NewsMVPPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule(var news: NewsMVPContract.View) {
    @Provides

    //--
    fun provideNewsPresenter(): NewsMVPContract.Presenter = NewsMVPPresenter(news)
}