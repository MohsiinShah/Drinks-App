package com.mohsin.drinksapp.ui.favorties

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mohsin.drinksapp.data.local.DrinksDatabase
import com.mohsin.drinksapp.data.models.FavDrink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(val db: DrinksDatabase) : ViewModel() {

    val getFavoriteDrinks = db.favsDao().getAllDrinks().asLiveData()

    fun removeFavoriteDrink(drink: FavDrink) {
        viewModelScope.launch {
            db.favsDao().deleteFavDrink(drink)
        }
    }
}