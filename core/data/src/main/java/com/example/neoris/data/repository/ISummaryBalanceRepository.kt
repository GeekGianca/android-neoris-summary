package com.example.neoris.data.repository

import com.example.neoris.core.model.BalanceSummaryModel
import com.example.neoris.shared.result.Result
import kotlinx.coroutines.flow.Flow

interface ISummaryBalanceRepository {
    fun summaryBalance(): Flow<Result<BalanceSummaryModel>>
}