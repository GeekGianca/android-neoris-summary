package com.example.neoris.shared.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable, val message: String? = "") : Result<Nothing>
    data object Loading : Result<Nothing>
    data object Empty: Result<Nothing>
}

val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data

val <T> Result<T>.result: T
    get() = (this as? Result.Success)?.data!!

fun <T> Flow<T>.asResult(): Flow<Result<T>> =
    map<T, Result<T>> { Result.Success(it) }
        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(it)) }