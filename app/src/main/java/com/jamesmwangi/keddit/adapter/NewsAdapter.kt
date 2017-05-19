package com.jamesmwangi.keddit.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.jamesmwangi.keddit.commons.adapter.AdapterConstants
import com.jamesmwangi.keddit.commons.adapter.ViewType
import com.jamesmwangi.keddit.commons.adapter.ViewTypeDelegateAdapter
import com.jamesmwangi.keddit.commons.models.RedditNewsItem

class NewsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private var loadingItem = object :  ViewType {
        override fun getViewType() = AdapterConstants.LOADING
    }

    init {
        delegateAdapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConstants.NEWS, NewsDelegateAdapter())
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return this.items.get(position).getViewType()
    }

    fun addNews(news: List<RedditNewsItem>){
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        items.addAll(news)
        items.add(loadingItem)
        notifyItemChanged(initPosition, items.size + 1)
    }

    fun clearAndAddNews(news: List<RedditNewsItem>){
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)

    }

    fun getNews(): List<RedditNewsItem>{
        return items.filter { it.getViewType() == AdapterConstants.NEWS }
                .map { it as RedditNewsItem }
    }

    private fun getLastPosition() = if (items.lastIndex == -1 ) 0 else items.lastIndex
}