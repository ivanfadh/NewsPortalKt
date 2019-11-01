package com.example.newsportal.view.newslist

import com.example.newsportal.model.ArticlesItem
import com.example.newsportal.model.Source

interface NewsMVPContract {

    interface Model {
        interface OnFinishedListener {
            fun onFinished(articlesItems: List<ArticlesItem>)

            fun onFailure(t: String)
        }

        interface onFinishedNews {
            fun onFinishedSource(newsSource: String)

            fun onFailureSource(t: String)
        }
        fun getNewsList(onFinishedListener: OnFinishedListener, pageNo: Int)

//        fun getNewsSource(onFinishedNews: onFinishedNews)
    }

    interface View {
        fun showLoading(loading: Boolean)
        fun setNews(articlesItems: List<ArticlesItem>)
        fun onResponseFailure(reason: String)

    }

    interface Presenter {
        //callNews
        fun requestDataFromServer()
    }
}
