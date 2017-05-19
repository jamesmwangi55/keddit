package com.jamesmwangi.keddit.di.news

import com.jamesmwangi.keddit.api.NewsAPI
import com.jamesmwangi.keddit.api.NewsRestAPI
import com.jamesmwangi.keddit.api.RedditApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by james on 5/19/2017.
 */
@Module
class NewsModule {
    @Provides
    @Singleton
    fun provideNewsAPI(redditApi: RedditApi): NewsAPI{
        return NewsRestAPI(redditApi)
    }
    @Provides
    @Singleton
    fun provideRedditApi(retrofit: Retrofit): RedditApi{
        return retrofit.create(RedditApi::class.java)
    }
}