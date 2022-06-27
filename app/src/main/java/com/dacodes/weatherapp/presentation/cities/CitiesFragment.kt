package com.dacodes.weatherapp.presentation.cities

import android.os.Bundle
import androidx.fragment.app.viewModels
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import com.dacodes.weatherapp.R
import com.dacodes.weatherapp.core.presentation.BaseFragment
import com.dacodes.weatherapp.core.presentation.EventObserver
import com.dacodes.weatherapp.databinding.CitiesFragmentBinding
import com.dacodes.weatherapp.domain.exceptions.NotFoundException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitiesFragment : BaseFragment<CitiesFragmentBinding>(R.layout.cities_fragment) {

    private val citiesViewModel by viewModels<CitiesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = citiesViewModel

        binding.apply {

            btnSearch.setOnClickListener {
                searchCity()
            }

            etCity.setOnEditorActionListener { _, actionID, _ ->
                if (actionID == EditorInfo.IME_ACTION_SEARCH){
                    searchCity()
                    true
                }else{
                    false
                }
            }

            etCity.doOnTextChanged { text, _, _, _ ->
                text?.let {
                    btnSearch.isEnabled = it.isNotEmpty()
                }
            }

            citiesViewModel.cityInfo.observe(viewLifecycleOwner, EventObserver{ result ->
                handleViewModelResult(
                    result,
                    { city ->
                        tvInfo.text = "${getString(R.string.city_name)} ${city.name} \n\n${getString(R.string.country_code)} ${city.country} \n\n${getString(R.string.latitude)} ${city.latitude}\n\n${getString(R.string.longitude)} ${city.longitude}"
                    },
                    { error, ex ->
                        when(ex){
                            is NotFoundException -> {
                                tvInfo.text = getString(R.string.not_found)
                            }
                            else -> {
                                showErrorMessage(error)
                            }
                        }
                    },
                    loader::handleLoaderDialog
                )
            })
        }

    }

    private fun searchCity(){
        citiesViewModel.searchCityInfo()
        hideKeyboard()
    }

}