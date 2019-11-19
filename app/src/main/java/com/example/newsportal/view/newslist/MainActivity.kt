package com.example.newsportal.view.newslist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.newsportal.R
import com.example.newsportal.model.ArticlesItem
import com.example.newsportal.adapter.NewsAdapter
import com.example.newsportal.application.NewsApplication
import com.example.newsportal.di.DaggerAppComponent
import com.example.newsportal.di.DaggerMainComponent
import com.example.newsportal.di.PresenterModule
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NewsMVPContract.View {
    override fun setNews(articlesItems: List<ArticlesItem>) {
        adapter = NewsAdapter(this@MainActivity, articlesItems)
        recyclerView?.adapter = adapter
    }

    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null

    private var layoutManager: RecyclerView.LayoutManager? = null
     @Inject lateinit var presenter: NewsMVPContract.Presenter

    lateinit var loadingLayout: LinearLayout
    lateinit var responseLayout: LinearLayout
    lateinit var tvResponse: TextView
    lateinit var buttonReload: Button


    override fun onCreate(savedInstanceState: Bundle?) {

        val component = DaggerMainComponent.builder()
                .presenterModule(PresenterModule(this as NewsMVPContract.View)).build()

        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
//        presenter = NewsMVPPresenter(this)
        loadingLayout = findViewById(R.id.layout_loading)
        responseLayout = findViewById(R.id.layout_error)
        tvResponse = findViewById(R.id.tvError)
        buttonReload = findViewById(R.id.buttonReload)
        presenter.requestDataFromServer()

        recyclerView?.addOnScrollListener(object: RecyclerView.OnScrollListener(){})
    }

    override fun showLoading(loading: Boolean) {
        if (loading) {
            responseLayout.visibility = View.GONE
            loadingLayout.visibility = View.VISIBLE
        } else {
            loadingLayout.visibility = View.GONE
        }

    }

 /*   override fun setNews(articlesItems: List<ArticlesItem>) {
        adapter = NewsAdapter(this@MainActivity, articlesItems)
        recyclerView?.adapter = adapter
    }*/

    override fun onResponseFailure(reason: String) {
        tvResponse.text = reason
        responseLayout.visibility = View.VISIBLE
        buttonReload.setOnClickListener { presenter.requestDataFromServer() }
    }

    companion object {

        private val NEWS_ID = "id"
    }

}

