package com.example.newsportal.network

import com.example.newsportal.model.ArticlesItem
import com.example.newsportal.model.ArticlesResponse
import com.example.newsportal.model.Source
import io.reactivex.Observable
import io.reactivex.Single

import retrofit2.http.GET

interface ApiInterface {
    @GET("v2/top-headlines/?country=id&apiKey=f702444a43234cdd90a4521f712aec88")
//    val news: Call<ArticlesResponse>
    fun news(): Single<ArticlesResponse>

//    val news: Observable<List<ArticlesResponse>>
/*
    @get:GET("v2/top-headlines?country=id&apiKey=f702444a43234cdd90a4521f712aec88")
    val newsSource: Single<Source>*/

}
