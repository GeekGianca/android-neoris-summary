package com.example.neoris.domain

import com.example.neoris.core.model.BalanceSummaryModel
import com.example.neoris.data.repository.ISummaryBalanceRepository
import com.example.neoris.shared.result.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBalanceSummaryUseCase @Inject constructor(
    private val summaryRepository: ISummaryBalanceRepository
) {
    operator fun invoke(): Flow<Result<BalanceSummaryModel>> = summaryRepository.summaryBalance()
}