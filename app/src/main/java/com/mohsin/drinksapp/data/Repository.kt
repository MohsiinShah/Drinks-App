package com.mohsin.drinksapp.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.room.withTransaction
import com.mohsin.drinksapp.api.DrinksApi
import com.mohsin.drinksapp.data.local.DrinksDatabase
import com.mohsin.drinksapp.utils.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject

class Repository
    @Inject constructor(
        private val api: DrinksApi,
        private val db: DrinksDatabase
) {

    private val drinksDao = db.drinksDao()
    private val favsDao = db.favsDao()


    fun getDrinksByName(characs: String, searchByName: Boolean) = networkBoundResource(
        query = {
            drinksDao.getAllDrinks()
        },
        fetch = {
            delay(1000)

            if(searchByName)
            api.getDrinksByName(characs)
            else
            api.getDrinksByAlphabet(characs)
        },
        saveFetchResult = { drinks ->
            db.withTransaction {
                drinksDao.deleteAllDrinks()
                drinks.drinks?.let { drinksDao.insertDrinks(it) }
            }
        }
    )
}