package com.dacodes.weatherapp.core.presentation

import javax.inject.Inject

class ErrorParser @Inject constructor() {
    operator fun invoke(ex: Exception): String =
        ex.message ?: "Ocurrió un error, intentalo más tarde"
}