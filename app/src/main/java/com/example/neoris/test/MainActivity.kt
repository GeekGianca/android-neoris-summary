package com.example.neoris.test

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neoris.core.model.TransactionModel
import com.example.neoris.test.databinding.ActivityMainBinding
import com.example.neoris.test.ui.TransactionAdapter
import com.example.neoris.test.ui.viewmodel.MainViewModel
import com.example.neoris.test.ui.viewmodel.SummaryUiState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var transactionAdapter: TransactionAdapter
    private val viewModel: MainViewModel  by viewModels()
    private val decimalFormat = DecimalFormat("#,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        observeViewModel()
        setupErrorHandling()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { result ->
                handleResult(result)
            }
        }
    }

    private fun handleResult(result: SummaryUiState) {
        when (result) {
            SummaryUiState.Empty -> {

            }
            is SummaryUiState.Error -> {
                binding.progressBar.isVisible = false
                binding.rvTransactions.isVisible = false
                showError(result.message)
            }
            is SummaryUiState.Loading -> {
                showLoading(result.state)
            }
            is SummaryUiState.Success -> {
                displayTransactions(result.data.transactions)
            }
        }
    }

    private fun setupErrorHandling() {
        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                        .setAction("Reintentar") { viewModel.retry() }
                        .show()
                    viewModel.clearError()
                }
            }
        }
    }
    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter { transaction ->
            onTransactionClick(transaction)
        }

        with(binding.rvTransactions) {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }

    private fun displayTransactions(transactions: List<TransactionModel>) {
        transactionAdapter.submitList(transactions)
        calculateTotals(transactions)
    }

    private fun calculateTotals(transactions: List<TransactionModel>) {
        var balance = 0.0
        var totalIncome = 0.0
        var totalExpenses = 0.0
        transactions.forEach { transaction ->
            if (transaction.type.equals("ingreso", ignoreCase = true)) {
                totalIncome += transaction.amount
                balance += transaction.amount
            } else {
                totalExpenses += abs(transaction.amount)
                balance += transaction.amount
            }
        }
        with(binding) {
            tvSaldoTotal.text = "$${decimalFormat.format(balance)}"
            tvIngresosTotales.text = "$${decimalFormat.format(totalIncome)}"
            tvEgresosTotales.text = "$${decimalFormat.format(totalExpenses)}"
        }
    }

    private fun showLoading(show: Boolean) {
        with(binding) {
            if (show) {
                progressBar.visibility = View.VISIBLE
                rvTransactions.visibility = View.GONE
                tvError.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                rvTransactions.visibility = View.VISIBLE
            }
        }
    }

    private fun showError(message: String) {
        with(binding) {
            progressBar.visibility = View.GONE
            rvTransactions.visibility = View.GONE
            tvError.visibility = View.VISIBLE
            tvError.text = message
        }
    }

    private fun onTransactionClick(transaction: TransactionModel) {
        println("Clicked transaction: ${transaction.name}")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}