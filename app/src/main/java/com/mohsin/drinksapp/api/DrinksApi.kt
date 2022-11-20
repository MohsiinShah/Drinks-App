package com.mohsin.drinksapp.api

import com.mohsin.drinksapp.data.models.Drink
import com.mohsin.drinksapp.data.models.DrinksRecipes
import com.mohsin.drinksapp.utils.UrlUtils
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinksApi {

    @GET(UrlUtils.GET_DRINKS)
    suspend fun getDrinksByName(@Query("s") name: String): DrinksRecipes

    @GET(UrlUtils.GET_DRINKS)
    suspend fun getDrinksByAlphabet(@Query("f") alphabet: String): DrinksRecipes
}