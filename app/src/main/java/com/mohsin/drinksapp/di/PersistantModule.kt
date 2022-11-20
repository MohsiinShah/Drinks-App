package com.mohsin.drinksapp.di

import android.content.Context
import android.content.SharedPreferences
import com.mohsin.drinksapp.utils.Constants
import com.mohsin.drinksapp.utils.MyAppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistantModule {

    @Singleton
    @Provides
    fun provideMyAppPreferences(preferences: SharedPreferences): MyAppPreferences {
        return MyAppPreferences(preferences)
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            Constants.APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

}