package com.mohsin.drinksapp.ui.favorties

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mohsin.drinksapp.R
import com.mohsin.drinksapp.data.models.FavDrink
import com.mohsin.drinksapp.databinding.DrinkItemBinding

class FavoritesAdapter(private val favoritesNavigator: FavoritesNavigator) :
    ListAdapter<FavDrink, FavoritesAdapter.FavoritesViewHolder>(DrinkComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding =
            DrinkItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, favoritesNavigator)
        }
    }

    class FavoritesViewHolder(private val binding: DrinkItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(drink: FavDrink, favoritesNavigator: FavoritesNavigator) {
            binding.apply {
                Glide.with(itemView)
                    .load(drink.strDrinkThumb)
                    .into(drinkThumbnail)

                drinkName.text = drink.strDrink
                drinkInstructions.text = drink.strInstructions
                alcoholCheckBox.isChecked = drink.strAlcoholic == "Alcoholic"

                favoriteIcon.background = if (drink.isFavorite) {
                    ContextCompat.getDrawable(binding.root.context, R.drawable.ic_baseline_star_24)
                } else ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.ic_baseline_star_border_unfilled_24
                )

                favoriteIcon.apply {
                    setOnClickListener {
                        drink.isFavorite = !drink.isFavorite

                        it.background =
                            if (drink.isFavorite) {
                                ContextCompat.getDrawable(
                                    binding.root.context,
                                    R.drawable.ic_baseline_star_24
                                )
                            } else ContextCompat.getDrawable(
                                binding.root.context,
                                R.drawable.ic_baseline_star_border_unfilled_24
                            )

                        favoritesNavigator.onFavoriteClick(drink)
                    }
                }
            }
        }
    }

    class DrinkComparator : DiffUtil.ItemCallback<FavDrink>() {
        override fun areItemsTheSame(oldItem: FavDrink, newItem: FavDrink) =
            oldItem.idDrink == newItem.idDrink

        override fun areContentsTheSame(oldItem: FavDrink, newItem: FavDrink) =
            oldItem == newItem
    }
}