package com.mohsin.drinksapp.di

import android.app.Application
import androidx.room.Room
import com.mohsin.drinksapp.api.DrinksApi
import com.mohsin.drinksapp.data.Repository
import com.mohsin.drinksapp.data.local.DrinksDatabase
import com.mohsin.drinksapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRepository(apiService: DrinksApi, db: DrinksDatabase): Repository {
        return Repository(apiService, db)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): DrinksDatabase =
        Room.databaseBuilder(app, DrinksDatabase::class.java, Constants.ROOM.DATABASE_NAME)
            .build()
}
