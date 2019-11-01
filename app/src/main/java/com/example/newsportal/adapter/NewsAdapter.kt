package com.example.newsportal.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.newsportal.R
import com.example.newsportal.model.ArticlesItem
import com.example.newsportal.view.newsdetail.DetailActivity

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

class NewsAdapter(private var context: Context, private var mNewsList: List<ArticlesItem>) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val mView = LayoutInflater.from(context).inflate(R.layout.news_list, parent, false)

        //hubungkan dengan holder
        return NewsHolder(mView)
    }


    override fun onBindViewHolder(holder: NewsHolder, position: Int) {

        holder.tvTitle.text = mNewsList[position].title


        val string = "2019-11-01T04:00:00.000Z"
        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date = LocalDate.parse(string, formatter)

        holder.tvDate.text = date.toString()

        holder.tvPublisher.text = mNewsList[position].source!!.name

        Glide
                .with(context)
                .load(mNewsList[position].urlToImage)
                .centerCrop()
                .into(holder.ivNews)

        holder.itemView.setOnClickListener {
            val varIntent = Intent(context, DetailActivity::class.java)
            varIntent.putExtra("TITLE", mNewsList[position].title)
            varIntent.putExtra("DATE", date.toString())
            varIntent.putExtra("PUBLISHER", mNewsList[position].source!!.name)
            varIntent.putExtra("IMAGE", mNewsList[position].urlToImage)
            varIntent.putExtra("NEWS", mNewsList[position].content)


            // method startActivity can only be use in activity/fragment
            context.startActivity(varIntent)
        }
    }

    inner class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var tvDate: TextView = itemView.findViewById(R.id.tvDate)
        var tvPublisher: TextView = itemView.findViewById(R.id.tvPublisher)
        var ivNews: ImageView = itemView.findViewById(R.id.ivNews)

    }

    override fun getItemCount(): Int {

        return mNewsList.size
    }

}
