package com.example.newsportal.view.newslist

import com.example.newsportal.model.ArticlesResponse
import com.example.newsportal.model.Source
import com.example.newsportal.network.ApiClient
import com.example.newsportal.network.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class NewsMVPModel : NewsMVPContract.Model {
    lateinit var apiInterface: ApiInterface
    lateinit var apiInterface2: ApiInterface

    //can call and dispose all at once
    private val disposable = CompositeDisposable()

/*    override fun getNewsSource(onFinishedNews: NewsMVPContract.Model.onFinishedNews) {
        apiInterface2 = ApiClient.client?.create(ApiInterface::class.java)!!

        disposable.add(apiInterface2.news
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Source>() {
                    override fun onSuccess(t: Source) {
                        val articlesName = t.name
                        articlesName?.let { onFinishedNews.onFinishedSource(it) }
                    }

                    override fun onError(e: Throwable) {
                        e.message?.let { onFinishedNews.onFailureSource(it) }

                    }
                }))
    }*/

    override fun getNewsList(onFinishedListener: NewsMVPContract.Model.OnFinishedListener, pageNo: Int) {
        apiInterface = ApiClient.client?.create(ApiInterface::class.java)!!

        disposable.add(apiInterface.news
                //tell observable to run taks on the background thread
                .subscribeOn(Schedulers.io())
                //tells Observer to receive the data on android UI thread so that you can take any UI related actions
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArticlesResponse>() {
                    override fun onSuccess(articles: ArticlesResponse) {
                        val articlesItems = articles.articles
                        articlesItems?.let { onFinishedListener.onFinished(it) }
                    }

                    override fun onError(e: Throwable) {
                        e.message?.let { onFinishedListener.onFailure(it) }

                    }
                }))

    }


    }


  /*  override fun getNewsList(onFinishedListener: NewsMVPContract.Model.OnFinishedListener, pageNo: Int) {
        apiInterface = ApiClient.client?.create(ApiInterface::class.java)!!

        disposable.add(apiInterface.news
                //tell observable to run taks on the background thread
                .subscribeOn(Schedulers.io())
                //tells Observer to receive the data on android UI thread so that you can take any UI related actions
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArticlesResponse>() {
                    override fun onSuccess(articles: ArticlesResponse) {
                        val articlesItems = articles.articles
                        articlesItems?.let { onFinishedListener.onFinished(it) }
                    }

                    override fun onError(e: Throwable) {
                        e.message?.let { onFinishedListener.onFailure(it) }

                    }
                }))

    }
*/

/*        val articlesResponseCall = apiInterface.news
        articlesResponseCall.enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(call: Call<ArticlesResponse>, response: Response<ArticlesResponse>) {
                val articlesItems = response.body()?.articles
                articlesItems?.let { onFinishedListener.onFinished(it) }
                //                view.showLoading(false);
                //                view.setNews(articlesItems);
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                t.message?.let { onFinishedListener.onFailure(it) }
                //onFinishedListener.onFailure(t.message)

            }

        })*/