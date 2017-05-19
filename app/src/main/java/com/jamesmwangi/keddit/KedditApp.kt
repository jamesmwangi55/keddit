package com.jamesmwangi.keddit

import android.app.Application
import com.jamesmwangi.keddit.di.AppModule
import com.jamesmwangi.keddit.di.news.DaggerNewsComponent
import com.jamesmwangi.keddit.di.news.NewsComponent
import com.jamesmwangi.keddit.di.news.NewsModule

/**
 * Created by james on 5/19/2017.
 */
class KedditApp: Application() {

    companion object{
        lateinit var newsComponent: NewsComponent
    }

    override fun onCreate() {
        super.onCreate()
        newsComponent = DaggerNewsComponent.builder()
                .appModule(AppModule(this))
                .newsModule(NewsModule())
                .build();
    }


}