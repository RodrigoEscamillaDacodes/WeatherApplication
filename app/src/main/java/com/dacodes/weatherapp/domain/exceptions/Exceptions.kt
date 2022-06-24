package com.dacodes.weatherapp.domain.exceptions


class NetworkException(error: String, ex:Throwable) : RuntimeException(error, ex)
class NotFoundException() : RuntimeException()
