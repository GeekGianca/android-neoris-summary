package com.example.neoris.network.client

import com.example.neoris.core.model.BalanceSummaryModel
import retrofit2.http.GET

interface NetworkClient {
    @GET("b/687e9f36f7e7a370d1eb9ee0")
    suspend fun getFinancialSummary(): BalanceSummaryModel
}