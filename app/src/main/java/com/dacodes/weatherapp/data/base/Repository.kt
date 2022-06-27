package com.dacodes.weatherapp.data.base

import android.content.Context
import android.util.Log
import com.dacodes.weatherapp.core.presentation.CoroutineDispatchers
import com.dacodes.weatherapp.data.model.ApiError
import com.dacodes.weatherapp.domain.exceptions.NetworkException
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class Repository(
    protected val coroutineDispatchers: CoroutineDispatchers,
    private val context: Context
) {

    protected suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = coroutineDispatchers.io,
        block: suspend () -> T,
    ): T = withContext(dispatcher) {
        try {
            block()
        } catch (ex: Throwable) {
            Log.v("Repository.safeApiCall", ex.toString())
            when (ex) {
                is HttpException -> {
                    val apiError = convertErrorBody(ex)
                    throw NetworkException(
                        apiError?.message ?: ex.localizedMessage,
                        ex
                    )
                }
                else -> throw NetworkException(ex.localizedMessage ?: "error desconocido", ex)
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ApiError? {
        return try {
            throwable.response()?.errorBody()?.string()?.let {
                Gson().fromJson(it, ApiError::class.java)
            }
        } catch (exception: Exception) {
            null
        }
    }

}