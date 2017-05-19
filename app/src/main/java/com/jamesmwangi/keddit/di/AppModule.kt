package com.jamesmwangi.keddit.di

import android.content.Context
import com.jamesmwangi.keddit.KedditApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by james on 5/19/2017.
 */
@Module
class AppModule(val app: KedditApp) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideApplication(): KedditApp{
        return app;
    }
}