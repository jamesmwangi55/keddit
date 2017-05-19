package com.jamesmwangi.keddit.di.news

import com.jamesmwangi.keddit.NewsFragment
import com.jamesmwangi.keddit.di.AppModule
import com.jamesmwangi.keddit.di.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by james on 5/19/2017.
 */
@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NewsModule::class,
        NetworkModule::class
))
interface NewsComponent {
    fun inject(newsFragment: NewsFragment)
}