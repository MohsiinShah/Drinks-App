package com.mohsin.drinksapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsin.drinksapp.data.Repository
import com.mohsin.drinksapp.data.local.DrinksDatabase
import com.mohsin.drinksapp.data.models.Drink
import com.mohsin.drinksapp.data.models.toFavoriteDrink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: Repository,
    val db: DrinksDatabase
) : ViewModel() {

    fun getDrinks(name: String, flag: Boolean) =
        repository.getDrinksByName(name, flag).map { drinks ->
            viewModelScope.launch(Dispatchers.IO) {
                db.favsDao().getAllDrinks().collect {
                    if (drinks.data != null) {
                        for (drink in drinks.data) {
                            val exists =
                                it.filter { fav -> drink.idDrink == fav.idDrink }.isNotEmpty()
                            if (exists) {
                                drink.isFavorite = true
                            }
                        }
                    }
                }
            }
            delay(100)

            drinks
        }

    fun addFavoriteDrink(drink: Drink, filePath: String) {
        viewModelScope.launch {
            db.drinksDao().updateDrink(drink)
            drink.strDrinkThumb = filePath
            db.favsDao().insertDrink(toFavoriteDrink(drink))
        }
    }

    fun removeFavoriteDrink(drink: Drink) {
        viewModelScope.launch {
            db.drinksDao().updateDrink(drink)
            db.favsDao().deleteFavDrink(toFavoriteDrink(drink))
        }
    }
}