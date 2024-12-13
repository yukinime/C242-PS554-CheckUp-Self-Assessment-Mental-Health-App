package com.aruel.checkupapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aruel.checkupapp.data.response.DataItem
import com.aruel.checkupapp.databinding.ItemArticleBinding

class ArticleAdapter(private val articles: List<DataItem?>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: DataItem) {
            binding.tvTitle.text = article.title
            binding.tvSnippet.text = article.snippet
            binding.tvLink.text = article.link
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        articles[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = articles.size
}