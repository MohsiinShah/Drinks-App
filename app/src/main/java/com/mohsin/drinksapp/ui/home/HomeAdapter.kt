package com.mohsin.drinksapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mohsin.drinksapp.R
import com.mohsin.drinksapp.data.models.Drink
import com.mohsin.drinksapp.databinding.DrinkItemBinding

class HomeAdapter(val homeNavigator: HomeNavigator) :
    ListAdapter<Drink, HomeAdapter.HomeViewHolder>(DrinkComparator()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            DrinkItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, homeNavigator)
        }
    }

    class HomeViewHolder(private val binding: DrinkItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(drink: Drink, homeNavigator: HomeNavigator) {
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

                        homeNavigator.onFavoriteClick(drink)

                    }
                }
            }
        }
    }


    class DrinkComparator : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink) =
            oldItem.idDrink == newItem.idDrink

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink) =
            oldItem == newItem
    }

}