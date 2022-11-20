package com.mohsin.drinksapp.ui.favorties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohsin.drinksapp.data.models.FavDrink
import com.mohsin.drinksapp.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoritesNavigator {

    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favAdapter = FavoritesAdapter(this)

        binding.apply {

            recyclerView.apply {
                adapter = favAdapter
                layoutManager = LinearLayoutManager(context)
            }

            lifecycleScope.launch {
                viewModel.getFavoriteDrinks.observe(viewLifecycleOwner) {
                    favAdapter.submitList(it)

                    lifecycleScope.launch{
                        delay(500)
                        if(it.isEmpty()) errorTv.visibility = View.VISIBLE
                    }
                }
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavoriteClick(drink: FavDrink) {
        lifecycleScope.launch(Dispatchers.IO) {
            if (!drink.isFavorite) {
                viewModel.removeFavoriteDrink(drink)
            }
        }
    }
}