package com.example.neoris.core.model

data class TransactionModel(
    val amount: Double,
    val date: String,
    val id: Int,
    val name: String,
    val type: String
)