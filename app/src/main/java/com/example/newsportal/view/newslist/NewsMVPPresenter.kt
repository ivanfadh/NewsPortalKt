package com.example.newsportal.view.newslist

import com.example.newsportal.model.ArticlesItem
import com.example.newsportal.model.Source


class NewsMVPPresenter(private val newsMVPView: NewsMVPContract.View?) : NewsMVPContract.Presenter, NewsMVPContract.Model.OnFinishedListener {
    private val newsMVPModel: NewsMVPContract.Model

    init {
        newsMVPModel = NewsMVPModel()
    }

    override fun requestDataFromServer() {

        newsMVPView?.showLoading(true)

        newsMVPModel.getNewsList(this, 1)
    }

    override fun onFinished(articlesItems: List<ArticlesItem>) {
        newsMVPView!!.setNews(articlesItems)
        newsMVPView.showLoading(false)


    }

    override fun onFailure(t: String) {
        newsMVPView!!.onResponseFailure(t)
        newsMVPView.showLoading(false)

    }

}