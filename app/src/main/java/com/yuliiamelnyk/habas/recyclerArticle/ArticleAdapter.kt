package com.yuliiamelnyk.habas.recyclerArticle

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.yuliiamelnyk.habas.R
import com.yuliiamelnyk.habas.model.Article

/**
 * @project Habas
 * @author yuliiamelnyk on 09/11/2020
 */
class MyAdapter(listArray: ArrayList<Article>, mContext: Context) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var listArticleArray = listArray
    var context = mContext


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val author = view.findViewById<TextView>(R.id.article_author)
        val date = view.findViewById<TextView>(R.id.article_date)
        val title = view.findViewById<TextView>(R.id.article_title)
        val category = view.findViewById<TextView>(R.id.article_category)
        val likes = view.findViewById<ImageView>(R.id.heart)
        val txtLikes = view.findViewById<TextView>(R.id.txtLikes)
        val bookmarks = view.findViewById<ImageView>(R.id.bookmark)
        val txtBookmarks = view.findViewById<TextView>(R.id.txtBookmarks)

        fun bind(listItem: Article, context: Context) {
            author.text = listItem.author.toString()
            date.text = listItem.date.toString()
            title.text = listItem.title
            category.text = listItem.category.toString()
            likes.setImageResource(listItem.imgLikes)
            txtLikes.text = listItem.txtLikes
            bookmarks.setImageResource(listItem.imgBookmarks)
            txtBookmarks.text = listItem.txtBookmarks
            itemView.setOnClickListener() {
                Toast.makeText(context, "Presed: ${title.text}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.item_article_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var listItem = listArticleArray.get(position)
        holder.bind(listItem, context)
    }

    override fun getItemCount(): Int {
        return listArticleArray.size
    }
}