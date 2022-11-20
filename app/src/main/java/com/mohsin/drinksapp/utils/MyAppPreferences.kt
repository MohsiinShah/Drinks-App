package com.mohsin.drinksapp.utils


import android.content.SharedPreferences

class MyAppPreferences(private var mSharedPreferences: SharedPreferences) {


    fun saveSearchFilterByName(searchFilter: Boolean) {
        mSharedPreferences
            .edit()
            .putBoolean(Constants.PREFERENCES.SEARCH_FILTER, searchFilter)
            .apply()
    }

    fun getSearchFilterByName(): Boolean {
        return mSharedPreferences
            .getBoolean(Constants.PREFERENCES.SEARCH_FILTER, true)
    }

}