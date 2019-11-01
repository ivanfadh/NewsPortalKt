package com.example.newsportal.view.newslist

import android.util.Log
import com.example.newsportal.model.ArticlesItem
import com.example.newsportal.model.ArticlesResponse
import com.example.newsportal.model.Source
import com.example.newsportal.network.ApiClient
import com.example.newsportal.network.ApiInterface
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class NewsMVPModel : NewsMVPContract.Model {
    lateinit var apiInterface: ApiInterface

    //can call and dispose all at once
    private val disposable = CompositeDisposable()


    override fun getNewsList(onFinishedListener: NewsMVPContract.Model.OnFinishedListener, pageNo: Int) {
        apiInterface = ApiClient.client?.create(ApiInterface::class.java)!!


        val articlesResponseSingle = apiInterface.news()

        val articleResponseUK = apiInterface.newsUK()

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
/*        Observable.concat(articlesResponseSingle, articleResponseUK)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArticlesResponse> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(articlesResponse: ArticlesResponse) {
                        val articlesItems = articlesResponse.articles
                        onFinishedListener.onFinished(articlesItems!!)
                        Log.e("news", articlesItems.toString())


                    }

                    override fun onError(e: Throwable) {
                        e.message?.let { onFinishedListener.onFailure(it) }

                    }

                    override fun onComplete() {

                    }
                })*/

/*        Observable.merge(articlesResponseSingle, articleResponseUK)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArticlesResponse> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(articlesResponse: ArticlesResponse) {
                        val articlesItems = articlesResponse.articles
                        onFinishedListener.onFinished(articlesItems!!)
                        Log.e("newsMerge", articlesItems.toString())


                    }

                    override fun onError(e: Throwable) {
                        e.message?.let { onFinishedListener.onFailure(it) }

                    }

                    override fun onComplete() {

                    }
                })*/


    }
}