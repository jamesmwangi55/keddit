package com.jamesmwangi.keddit

import com.jamesmwangi.keddit.api.NewsAPI
import com.jamesmwangi.keddit.commons.models.RedditNews
import com.jamesmwangi.keddit.commons.models.RedditNewsItem
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by james on 5/19/2017.
 */
@Singleton
class NewsManager @Inject constructor(private val api: NewsAPI) {
    fun getNews(after: String, limit: String = "10"): Observable<RedditNews> {
        return Observable.create{
            subscriber ->
                val callResponse = api.getNews(after, limit)
                val response = callResponse.execute()
                if(response.isSuccessful){
                    var dataResponse = response.body().data
                    val news = dataResponse.children.map {
                        val item = it.data
                        RedditNewsItem(item.author, item.author, item.num_comments,
                                item.created, item.thumbnail, item.url)
                    }

                    val redditNews = RedditNews(
                            dataResponse.after ?: "",
                            dataResponse.before ?: "",
                            news
                    )
                    subscriber.onNext(redditNews)
                    subscriber.onCompleted()
                } else {
                    subscriber.onError(Throwable(response.message()))
                }
        }
    }
}