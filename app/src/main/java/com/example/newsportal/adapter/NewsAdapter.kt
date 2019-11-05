package com.example.newsportal.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter

import com.example.newsportal.R
import com.example.newsportal.model.ArticlesItem
import com.example.newsportal.view.newsdetail.DetailActivity
import com.example.newsportal.view.newslist.MainActivity

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.w3c.dom.Text

class NewsAdapter(private var context: Context, private var mNewsList: List<ArticlesItem>) : RecyclerSwipeAdapter<NewsAdapter.NewsHolder>() {
    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val mView = LayoutInflater.from(context).inflate(R.layout.news_list, parent, false)
        //hubungkan dengan holder
        return NewsHolder(mView)
    }


    override fun onBindViewHolder(holder: NewsHolder, position: Int) {

        holder.tvTitle.text = mNewsList[position].title


        val string = "2019-11-05T04:00:00.000Z"
        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date = LocalDate.parse(string, formatter)

        holder.tvDate.text = date.toString()

        holder.tvPublisher.text = mNewsList[position].source!!.name

        Glide
                .with(context)
                .load(mNewsList[position].urlToImage)
                .centerCrop()
                .into(holder.ivNews)

       /* holder.itemView.setOnClickListener {
            val varIntent = Intent(context, DetailActivity::class.java)
            varIntent.putExtra("TITLE", mNewsList[position].title)
            varIntent.putExtra("DATE", date.toString())
            varIntent.putExtra("PUBLISHER", mNewsList[position].source!!.name)
            varIntent.putExtra("IMAGE", mNewsList[position].urlToImage)
            varIntent.putExtra("NEWS", mNewsList[position].content)


            // method startActivity can only be use in activity/fragment
            context.startActivity(varIntent)
        }*/

        holder.swipeLayout.showMode = SwipeLayout.ShowMode.PullOut

        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wraper))

        holder.swipeLayout.addSwipeListener(object : SwipeLayout.SwipeListener {
            override fun onStartOpen(layout: SwipeLayout) {

            }

            override fun onOpen(layout: SwipeLayout) {

            }

            override fun onStartClose(layout: SwipeLayout) {

            }

            override fun onClose(layout: SwipeLayout) {

            }

            override fun onUpdate(layout: SwipeLayout, leftOffset: Int, topOffset: Int) {

            }

            override fun onHandRelease(layout: SwipeLayout, xvel: Float, yvel: Float) {

            }
        })

        holder.archive.setOnClickListener { view -> Toast.makeText(view.context, "Clicked on Share " + holder.tvTitle.text.toString(), Toast.LENGTH_SHORT).show() }

        holder.edit.setOnClickListener { view -> Toast.makeText(view.context,"Clicked on Edit  " + holder.tvTitle.text.toString(), Toast.LENGTH_SHORT).show() }

        holder.delete.setOnClickListener { view -> Toast.makeText(view.context, "Clicked on Delete  " + holder.tvTitle.text.toString(), Toast.LENGTH_SHORT).show() }

        holder.swipeLayout.surfaceView.setOnLongClickListener {

            mItemManger.bindView(holder.itemView, position)
            true
        }



        mItemManger.bindView(holder.itemView, position)
    }

    inner class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var swipeLayout: SwipeLayout = itemView.findViewById(R.id.swipe)
        var delete: ImageView = itemView.findViewById(R.id.Delete)
        var edit: ImageView = itemView.findViewById(R.id.Edit)
        var archive: ImageView = itemView.findViewById(R.id.Archive)
        var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var tvDate: TextView = itemView.findViewById(R.id.tvDate)
        var tvPublisher: TextView = itemView.findViewById(R.id.tvPublisher)
        var ivNews: ImageView = itemView.findViewById(R.id.ivNews)

    }

    override fun getItemCount(): Int {

        return mNewsList.size
    }

}
