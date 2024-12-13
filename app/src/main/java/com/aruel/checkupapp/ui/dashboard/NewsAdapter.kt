package com.aruel.checkupapp.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aruel.checkupapp.R
import com.aruel.checkupapp.data.response.ArticlesItem
import com.bumptech.glide.Glide

class NewsAdapter(private val articles: List<ArticlesItem>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = articles.size

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvNewsTitle)
        private val image: ImageView = itemView.findViewById(R.id.ivNews)

        fun bind(article: ArticlesItem) {
            title.text = article.title
            Glide.with(itemView.context).load(article.urlToImage).into(image)
        }
    }
}