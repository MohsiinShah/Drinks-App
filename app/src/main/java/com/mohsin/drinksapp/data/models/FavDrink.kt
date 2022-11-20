package com.mohsin.drinksapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "fav_drinks")
class FavDrink (
    @SerializedName("idDrink")
    @PrimaryKey var idDrink: String = "",

    @SerializedName("strAlcoholic")
    var strAlcoholic: String? = "",

    @SerializedName("strInstructions")
    var strInstructions: String? = "",

    @SerializedName("strDrinkThumb")
    var strDrinkThumb: String = "",

    @SerializedName("strDrink")
    var strDrink: String = "",

    var isFavorite: Boolean = false
){
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}