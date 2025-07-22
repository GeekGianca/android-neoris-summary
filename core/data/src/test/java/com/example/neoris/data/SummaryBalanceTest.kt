package com.example.neoris.data

import com.example.neoris.core.model.TransactionModel
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class SummaryBalanceTest {

    private val useCase = GetBalanceSummaryUseCase()

    @Test
    fun `should calculate total income correctly`() {
        // Given
        val list = listOf(
            TransactionModel(
                id = 1,
                name = "Salario Mensual",
                amount = 3000.0,
                date = "2024-07-15",
                type = "ingreso"
            ),
            TransactionModel(
                id = 2,
                name = "Freelance",
                amount = 500.0,
                date = "2024-07-16",
                type = "ingreso"
            ),
            TransactionModel(
                id = 3,
                name = "Pago de Alquiler",
                amount = -850.0,
                date = "2024-07-17",
                type = "egreso"
            )
        )

        // When
        val result = useCase(list)

        // Then
        assertEquals(3500.0, result.totalIncome, 0.01)
    }

    @Test
    fun `should calculate total expenses correctly`() {
        // Given
        val list = listOf(
            TransactionModel(
                id = 1,
                name = "Salario Mensual",
                amount = 3000.0,
                date = "2024-07-15",
                type = "ingreso"
            ),
            TransactionModel(
                id = 2,
                name = "Compra en Supermercado",
                amount = -120.75,
                date = "2024-07-16",
                type = "egreso"
            ),
            TransactionModel(
                id = 3,
                name = "Pago de Alquiler",
                amount = -850.0,
                date = "2024-07-17",
                type = "egreso"
            )
        )

        // When
        val result = useCase(list)

        // Then
        assertEquals(970.75, result.totalExpenses, 0.01)
    }

    @Test
    fun `should calculate current balance correctly`() {
        // Given
        val list = listOf(
            TransactionModel(
                id = 1,
                name = "Salario Mensual",
                amount = 3000.0,
                date = "2024-07-15",
                type = "ingreso"
            ),
            TransactionModel(
                id = 2,
                name = "Compra en Supermercado",
                amount = -120.75,
                date = "2024-07-16",
                type = "egreso"
            ),
            TransactionModel(
                id = 3,
                name = "Pago de Alquiler",
                amount = -850.0,
                date = "2024-07-17",
                type = "egreso"
            )
        )

        // When
        val result = useCase(list)

        // Then
        assertEquals(2029.25, result.currentBalance, 0.01)
    }

    @Test
    fun `should handle empty list`() {
        // Given
        val emptyList = emptyList<TransactionModel>()

        // When
        val result = useCase(emptyList)

        // Then
        assertEquals(0.0, result.totalIncome, 0.01)
        assertEquals(0.0, result.totalExpenses, 0.01)
        assertEquals(0.0, result.currentBalance, 0.01)
    }

    @Test
    fun `should handle only income transactions`() {
        // Given
        val list = listOf(
            TransactionModel(
                id = 1,
                name = "Salario",
                amount = 2000.0,
                date = "2024-07-15",
                type = "ingreso"
            ),
            TransactionModel(
                id = 2,
                name = "Bonus",
                amount = 500.0,
                date = "2024-07-16",
                type = "ingreso"
            )
        )

        // When
        val result = useCase(list)

        // Then
        assertEquals(2500.0, result.totalIncome, 0.01)
        assertEquals(0.0, result.totalExpenses, 0.01)
        assertEquals(2500.0, result.currentBalance, 0.01)
    }

    @Test
    fun `should handle only expense transactions`() {
        // Given
        val list = listOf(
            TransactionModel(
                id = 1,
                name = "Alquiler",
                amount = -800.0,
                date = "2024-07-15",
                type = "egreso"
            ),
            TransactionModel(
                id = 2,
                name = "Comida",
                amount = -200.0,
                date = "2024-07-16",
                type = "egreso"
            )
        )

        // When
        val result = useCase(list)

        // Then
        assertEquals(0.0, result.totalIncome, 0.01)
        assertEquals(1000.0, result.totalExpenses, 0.01)
        assertEquals(-1000.0, result.currentBalance, 0.01)
    }

    @Test
    fun `should handle mixed positive and negative amounts correctly`() {
        // Given
        val list = listOf(
            TransactionModel(
                id = 1,
                name = "Ingreso con monto positivo",
                amount = 1000.0,
                date = "2024-07-15",
                type = "ingreso"
            ),
            TransactionModel(
                id = 2,
                name = "Egreso con monto negativo",
                amount = -500.0,
                date = "2024-07-16",
                type = "egreso"
            )
        )
        // When
        val result = useCase(list)
        // Then
        assertEquals(1000.0, result.totalIncome, 0.01)
        assertEquals(500.0, result.totalExpenses, 0.01)
        assertEquals(500.0, result.currentBalance, 0.01)
    }
}

class GetBalanceSummaryUseCase {

    operator fun invoke(transactions: List<TransactionModel>): BalanceSummary {
        val totalIncome = transactions
            .filter { it.type == "ingreso" }
            .sumOf { it.amount }

        val totalExpenses = transactions
            .filter { it.type == "egreso" }
            .sumOf { kotlin.math.abs(it.amount) }

        val currentBalance = totalIncome - totalExpenses

        return BalanceSummary(
            totalIncome = totalIncome,
            totalExpenses = totalExpenses,
            currentBalance = currentBalance
        )
    }
}

data class BalanceSummary(
    val totalIncome: Double,
    val totalExpenses: Double,
    val currentBalance: Double
)