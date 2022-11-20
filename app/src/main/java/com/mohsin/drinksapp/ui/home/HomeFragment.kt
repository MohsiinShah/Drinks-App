package com.mohsin.drinksapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.RadioGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohsin.drinksapp.R
import com.mohsin.drinksapp.data.models.Drink
import com.mohsin.drinksapp.databinding.FragmentHomeBinding
import com.mohsin.drinksapp.utils.MyAppPreferences
import com.mohsin.drinksapp.utils.Resource
import com.mohsin.drinksapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeNavigator {

    @Inject
    lateinit var sharedPrefs: MyAppPreferences

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private var homeAdapter: HomeAdapter? = null
    private var callByName: Boolean = true
    private var searchText: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeAdapter = HomeAdapter(this)

        fetchData()

        binding.apply {

            if(sharedPrefs.getSearchFilterByName()){
                searchByName.isChecked = true
                callByName = true
            }else{
                searchByalphabet.isChecked = true
                callByName = false
            }

            recyclerView.apply {
                adapter = homeAdapter
                layoutManager = LinearLayoutManager(context)
            }

            searchEt.doAfterTextChanged {
                searchText = it.toString()
            }

            searchEt.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    fetchData()
                    return@setOnEditorActionListener true
                }
                false
            }

            searchFilterRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                callByName = checkedId == R.id.searchByName
                sharedPrefs.saveSearchFilterByName(callByName)
            })
        }
    }

    private fun fetchData() {
        binding.apply {
            lifecycleScope.launch {
                viewModel.getDrinks(searchText, callByName).collect { result ->
                    homeAdapter?.submitList(result.data)
                    progressBar.isVisible = result is Resource.Loading
                    errorTv.isVisible = result is Resource.Error
                    errorTv.text = result.error?.localizedMessage
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavoriteClick(drink: Drink) {

        lifecycleScope.launch(Dispatchers.IO) {
            if (drink.isFavorite) {
                val filePath =
                    Utils.downloadAndSaveImage(drink.strDrinkThumb, drink.idDrink, requireContext())
                viewModel.addFavoriteDrink(drink, filePath)
            } else
                viewModel.removeFavoriteDrink(drink)
        }
    }
}