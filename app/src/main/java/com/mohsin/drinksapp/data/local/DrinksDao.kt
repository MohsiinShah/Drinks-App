package com.mohsin.drinksapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mohsin.drinksapp.data.models.Drink
import kotlinx.coroutines.flow.Flow


@Dao
interface DrinksDao {

    @Query("SELECT * FROM drinks")
    fun getAllDrinks(): Flow<List<Drink>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinks(drinks: List<Drink>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrink(drinks: Drink)

    @Query("DELETE FROM drinks")
    suspend fun deleteAllDrinks()

    @Update
    suspend fun updateDrink(drink: Drink)

    @Query("SELECT COUNT() FROM drinks WHERE idDrink = :id")
    suspend fun count(id: String): Int

    @Query("SELECT * FROM drinks WHERE idDrink = :id")
    suspend fun getSingleDrink(id: String): Drink
}