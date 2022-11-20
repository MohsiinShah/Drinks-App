package com.mohsin.drinksapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohsin.drinksapp.data.models.Drink
import com.mohsin.drinksapp.data.models.FavDrink

@Database(entities = [Drink::class, FavDrink::class], version = 1, exportSchema = false)
abstract class DrinksDatabase : RoomDatabase() {

    abstract fun drinksDao(): DrinksDao

    abstract fun favsDao(): FavsDao
}
