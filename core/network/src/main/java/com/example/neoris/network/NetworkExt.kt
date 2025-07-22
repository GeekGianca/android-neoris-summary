package com.example.neoris.network

import com.example.neoris.shared.exception.CommonException
import com.example.neoris.shared.exception.ExampleException
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.util.concurrent.TimeoutException

fun <T> Exception.errorCatch(): com.example.neoris.shared.result.Result<T> {
    return when (this) {
        is TimeoutCancellationException -> {
            com.example.neoris.shared.result.Result.Error(TimeoutException())
        }

        is HttpException -> {
            try {
                val res = this.response()?.errorBody()?.string()
                val parser = JsonParser.parseString(res)
                val gson = Gson()
                val errorRes = gson.fromJson(parser, Any::class.java)
                com.example.neoris.shared.result.Result.Error(CommonException(errorRes.toString()))
            } catch (e: Exception) {
                com.example.neoris.shared.result.Result.Error(TimeoutException())
            }

        }

        else -> com.example.neoris.shared.result.Result.Error(CommonException())
    }
}