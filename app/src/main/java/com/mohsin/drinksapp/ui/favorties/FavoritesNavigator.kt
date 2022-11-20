package com.mohsin.drinksapp.ui.favorties

import com.mohsin.drinksapp.data.models.Drink
import com.mohsin.drinksapp.data.models.FavDrink

interface FavoritesNavigator {

    fun onFavoriteClick(drink: FavDrink)
}