package com.mohsin.drinksapp.data.local

import androidx.room.*
import com.mohsin.drinksapp.data.models.Drink
import com.mohsin.drinksapp.data.models.FavDrink
import kotlinx.coroutines.flow.Flow

@Dao
interface FavsDao {
    @Query("SELECT * FROM fav_drinks")
    fun getAllDrinks(): Flow<List<FavDrink>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinks(drinks: List<FavDrink>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrink(drink: FavDrink)

    @Query("DELETE FROM fav_drinks")
    suspend fun deleteAllDrinks()

    @Delete
    suspend fun deleteFavDrink(drink: FavDrink)


}