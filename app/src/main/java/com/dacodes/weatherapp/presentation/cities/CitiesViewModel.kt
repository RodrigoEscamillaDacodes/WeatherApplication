package com.dacodes.weatherapp.presentation.cities

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dacodes.weatherapp.core.presentation.*
import com.dacodes.weatherapp.data.model.CityModel
import com.dacodes.weatherapp.domain.exceptions.NetworkException
import com.dacodes.weatherapp.domain.exceptions.NotFoundException
import com.dacodes.weatherapp.domain.model.City
import com.dacodes.weatherapp.usecases.GetCityInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    coroutineDispatchers: CoroutineDispatchers,
    errorParser: ErrorParser,
    application: Application,
    private val getCityInfoUseCase: GetCityInfoUseCase
): BaseViewModel(coroutineDispatchers, errorParser, application) {

    var cityName: String = ""

    private val _cityInfo = MutableLiveData<Event<ViewModelResult<City>>>()
    val cityInfo : LiveData<Event<ViewModelResult<City>>> get() = _cityInfo

    fun searchCityInfo() = results(_cityInfo){
        val cityResponse = getCityInfoUseCase(params = cityName)
        if(cityResponse.isEmpty()){
            _cityInfo.value = Event(ViewModelResult.Error("Not found",NotFoundException()))
        }else{
            _cityInfo.value = Event(ViewModelResult.Success(cityResponse.first()))
        }
    }

}