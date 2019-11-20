package com.example.newsportal.view.newslist

import com.example.newsportal.model.ArticlesItem
import com.example.newsportal.model.ArticlesResponse
import com.example.newsportal.network.ApiInterface
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Inject

class NewsMVPModel @Inject constructor(var apiInterface: ApiInterface?): NewsMVPContract.Model{

    override fun getNewsList(onFinishedListener: NewsMVPContract.Model.OnFinishedListener, pageNo: Int) {

        val articlesResponseSingle = apiInterface!!.news()

        val articleResponseUK = apiInterface!!.newsUK()

        Observable.zip(
                articleResponseUK, articlesResponseSingle,
                //High order function
                BiFunction <ArticlesResponse, ArticlesResponse, ArticlesResponse>  { articlesResponse, articlesResponse2 ->
                    //articleResponse UK, articleResponse2 INA
                    val result = ArticlesResponse()
                    result.totalResults = articlesResponse.totalResults + articlesResponse2.totalResults
                    val articlesItems = ArrayList<ArticlesItem>()
                    articlesItems.addAll(articlesResponse.articles!!)
                    articlesItems.addAll(articlesResponse2.articles!!)
                    result.articles = articlesItems
                    result
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArticlesResponse> {
                    override fun onSubscribe(d: Disposable){

                    }

                    override fun onNext(articlesResponse: ArticlesResponse) {
                        val articlesItems = articlesResponse.articles
                        onFinishedListener.onFinished(articlesItems!!)
                        //Log.e("newsMerge", articlesItems.toString());

                    }

                    override fun onError(e: Throwable) {
                        e.message?.let { onFinishedListener.onFailure(it) }

                    }

                    override fun onComplete() {

                    }
                })
    }
}