package com.dacodes.weatherapp.core.presentation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class CoroutineUseCase<in P, R>(
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(params: P): R = withContext(dispatcher) {
        execute(params)
    }

    protected abstract suspend fun execute(params: P): R
}

suspend operator fun <R> CoroutineUseCase<Unit, R>.invoke(): R = this(Unit)