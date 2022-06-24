package com.dacodes.weatherapp.core.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel(
    private val coroutineDispatchers: CoroutineDispatchers,
    private val errorParser: ErrorParser,
    application: Application
) : AndroidViewModel(application) {

    protected fun <T> result(
        liveData: MutableLiveData<ViewModelResult<T>>,
        dispatcher: CoroutineDispatcher = coroutineDispatchers.main,
        block: suspend () -> Unit,
    ): Job = viewModelScope.safeLaunch(dispatcher) {
        liveData.value = ViewModelResult.Loading
        try {
            block()
        } catch (ex: Exception) {

            val error = errorParser(ex)
            val viewModelResult = ViewModelResult.Error(error, ex)
            liveData.value = viewModelResult
        }
        this.cancel()
    }

    protected fun <T> results(
        liveData: MutableLiveData<Event<ViewModelResult<T>>>,
        dispatcher: CoroutineDispatcher = coroutineDispatchers.main,
        block: suspend () -> Unit,
    ): Job = viewModelScope.safeLaunch(dispatcher) {
        liveData.value = Event(ViewModelResult.Loading)
        try {
            block()
        } catch (ex: Exception) {

            val error = errorParser(ex)
            val viewModelResult = ViewModelResult.Error(error, ex)
            liveData.value = Event(viewModelResult)
        }
        dispatcher.cancel()
    }

    protected fun <T> result(
        stateFlow: MutableStateFlow<ViewModelResult<T>>,
        block: suspend () -> Unit,
    ): Job = viewModelScope.safeLaunch {
        stateFlow.value = ViewModelResult.Loading
        try {
            block()
        } catch (ex: Exception) {

            val error = errorParser(ex)
            val viewModelResult = ViewModelResult.Error(error, ex)
            stateFlow.value = viewModelResult
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

}