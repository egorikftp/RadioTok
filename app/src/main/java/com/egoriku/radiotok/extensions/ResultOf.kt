package com.egoriku.radiotok.extensions

sealed class ResultOf<out T> {
    class Success<out T>(val value: T) : ResultOf<T>()
    class Failure(val message: String) : ResultOf<Nothing>()
}