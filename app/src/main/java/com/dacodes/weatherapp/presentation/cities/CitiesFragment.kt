package com.dacodes.weatherapp.presentation.cities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import android.view.View
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

        binding.btnSearch.setOnClickListener {
            citiesViewModel.searchCityInfo()
        }

        binding.etCity.doOnTextChanged { text, _, _, _ ->
            text?.let {
                binding.btnSearch.isEnabled = it.isNotEmpty()
            }
        }

        citiesViewModel.cityInfo.observe(viewLifecycleOwner, EventObserver{ result ->
            handleViewModelResult(
                result,
                { city ->
                    binding.tvInfo.text = "Name: ${city.name} \n\nCountry: ${city.country} \n\nLatitude: ${city.latitude}, Longitude: ${city.longitude}"
                },
                { _, ex ->
                    when(ex){
                        is NotFoundException -> {
                            binding.tvInfo.text = getString(R.string.not_found)
                        }
                    }
                }
            )
        })


    }

}