package com.jamesmwangi.keddit.api

import retrofit2.Call
import javax.inject.Inject

/**
 * Created by james on 5/19/2017.
 */
class NewsRestAPI @Inject constructor(private val redditApi: RedditApi): NewsAPI {

    override fun getNews(after: String, limit: String): Call<RedditNewsResponse> {
        return redditApi.getTop(after, limit)
    }


}