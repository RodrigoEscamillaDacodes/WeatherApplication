package com.dacodes.weatherapp.core.presentation

sealed class ViewModelResult<out R> {

    data class Success<out T>(
        val data: T,
    ) : ViewModelResult<T>()

    data class Cache<out T>(
        val data: T,
    ) : ViewModelResult<T>()

    data class Error(
        val message: String,
        val ex: Exception?
    ) : ViewModelResult<Nothing>()

    object Loading : ViewModelResult<Nothing>()

}