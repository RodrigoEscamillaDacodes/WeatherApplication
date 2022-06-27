package com.dacodes.weatherapp.core.presentation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.safeLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit): Job {
    return this.launch(context) {
        try {
            block()
        } catch (ce: CancellationException) {
            // You can ignore or log this exception
        } catch (e: Exception) {
            // Here it's better to at least log the exception
        }
    }
}

fun String.clearText() : String{
    return replace(" ","")
}