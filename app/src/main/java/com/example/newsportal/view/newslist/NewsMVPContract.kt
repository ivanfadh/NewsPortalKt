package com.example.newsportal.view.newslist

import com.example.newsportal.model.ArticlesItem
import com.example.newsportal.model.Source

interface NewsMVPContract {

    interface Model {
        interface OnFinishedListener {
            fun onFinished(articlesItems: List<ArticlesItem>)

            fun onFailure(t: String)
        }

        fun getNewsList(onFinishedListener: OnFinishedListener, pageNo: Int)

    }

    interface View {
        fun showLoading(loading: Boolean): Any
        fun setNews(articlesItems: List<ArticlesItem>)
        fun onResponseFailure(reason: String)
//        fun setupRecyclerView()
    }

    interface Presenter {
        //callNews
        fun requestDataFromServer()
    }
}
