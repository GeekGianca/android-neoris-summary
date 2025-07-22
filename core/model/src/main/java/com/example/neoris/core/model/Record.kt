package com.example.neoris.core.model

data class Record(
    val balance: Double,
    val transactions: List<TransactionModel>
)