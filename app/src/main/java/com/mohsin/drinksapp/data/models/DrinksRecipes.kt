package com.mohsin.drinksapp.data.models


import com.google.gson.annotations.SerializedName

data class DrinksRecipes(
    @SerializedName("drinks")
    var drinks: List<Drink>? = listOf()
)