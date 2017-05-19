package com.jamesmwangi.keddit.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.jamesmwangi.keddit.R
import com.jamesmwangi.keddit.commons.adapter.ViewType
import com.jamesmwangi.keddit.commons.adapter.ViewTypeDelegateAdapter
import com.jamesmwangi.keddit.commons.extensions.getFriendlyTime
import com.jamesmwangi.keddit.commons.extensions.inflate
import com.jamesmwangi.keddit.commons.extensions.loadImage
import com.jamesmwangi.keddit.commons.models.RedditNewsItem
import kotlinx.android.synthetic.main.news_item.view.*

/**
 * Created by james on 5/18/2017.
 */
class NewsDelegateAdapter: ViewTypeDelegateAdapter {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder
        holder.bind(item as RedditNewsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TurnsViewHolder(parent)
    }

    class TurnsViewHolder(parent: ViewGroup):
            RecyclerView.ViewHolder(parent.inflate(R.layout.news_item)){
        fun bind(item: RedditNewsItem) = with(itemView){
            img_thumbnail.loadImage(item.thumbnail)
            description.text  = item.title
            author.text = item.author
            comments.text = "${item.numComments} comments"
            time.text = item.created.getFriendlyTime()
        }
    }

}