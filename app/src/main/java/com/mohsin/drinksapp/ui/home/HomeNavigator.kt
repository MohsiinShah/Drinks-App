package com.mohsin.drinksapp.ui.home

import com.mohsin.drinksapp.data.models.Drink

interface HomeNavigator {

    fun onFavoriteClick(drink: Drink)
}