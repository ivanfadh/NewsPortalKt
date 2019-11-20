package com.example.newsportal.di

import com.example.newsportal.network.ApiClient
import com.example.newsportal.network.ApiInterface
import com.example.newsportal.view.newslist.NewsMVPContract
import com.example.newsportal.view.newslist.NewsMVPModel
import com.example.newsportal.view.newslist.NewsMVPPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule(var news: NewsMVPContract.View) {
    @Provides
    //--
    fun provideNewsPresenter(newsMVPModel: NewsMVPModel): NewsMVPPresenter = NewsMVPPresenter(news, newsMVPModel)

    @Provides
    fun provideApiInterface(): ApiInterface? = ApiClient.client?.create(ApiInterface::class.java)

}