package com.example.neoris.test.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.neoris.core.model.TransactionModel
import com.example.neoris.test.R
import com.example.neoris.test.databinding.ItemTransactionBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.abs

class TransactionAdapter(
    private val onItemClick: (TransactionModel) -> Unit = {}
) : ListAdapter<TransactionModel, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TransactionViewHolder(
        private val binding: ItemTransactionBinding,
        private val onItemClick: (TransactionModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val decimalFormat = DecimalFormat("#,##0.00")
        private val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        private val outputDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        fun bind(transaction: TransactionModel) {
            with(binding) {
                tvTransactionName.text = transaction.name
                tvTransactionDate.text = formatDate(transaction.date)
                val isIncome = transaction.type.equals("ingreso", ignoreCase = true)
                if (isIncome) {
                    setupIncomeTransaction(transaction)
                } else {
                    setupExpenseTransaction(transaction)
                }
                val formattedAmount = if (isIncome) {
                    "+$${decimalFormat.format(transaction.amount)}"
                } else {
                    "-$${decimalFormat.format(abs(transaction.amount))}"
                }
                tvTransactionAmount.text = formattedAmount
                root.setOnClickListener {
                    onItemClick(transaction)
                }
            }
        }

        private fun setupIncomeTransaction(transaction: TransactionModel) {
            val context = binding.root.context
            with(binding) {
                tvTransactionType.text = "Ingreso"
                tvTransactionType.setTextColor(ContextCompat.getColor(context, R.color.income_color))
                tvTransactionType.setBackgroundResource(R.drawable.income_badge_background)
                ivTransactionIcon.setImageResource(R.drawable.ic_arrow_upward)
                ivTransactionIcon.setColorFilter(ContextCompat.getColor(context, R.color.income_color))
                cvIconContainer.setCardBackgroundColor(ContextCompat.getColor(context, R.color.income_light))
                tvTransactionAmount.setTextColor(ContextCompat.getColor(context, R.color.income_color))
                viewAmountIndicator.setBackgroundColor(ContextCompat.getColor(context, R.color.income_color))
            }
        }

        private fun setupExpenseTransaction(transaction: TransactionModel) {
            val context = binding.root.context

            with(binding) {
                tvTransactionType.text = "Egreso"
                tvTransactionType.setTextColor(ContextCompat.getColor(context, R.color.expense_color))
                tvTransactionType.setBackgroundResource(R.drawable.expense_badge_background)
                ivTransactionIcon.setImageResource(R.drawable.ic_arrow_downward)
                ivTransactionIcon.setColorFilter(ContextCompat.getColor(context, R.color.expense_color))
                cvIconContainer.setCardBackgroundColor(ContextCompat.getColor(context, R.color.expense_light))
                tvTransactionAmount.setTextColor(ContextCompat.getColor(context, R.color.expense_color))
                viewAmountIndicator.setBackgroundColor(ContextCompat.getColor(context, R.color.expense_color))
            }
        }

        private fun formatDate(dateString: String): String {
            return try {
                val date = inputDateFormat.parse(dateString)
                date?.let { outputDateFormat.format(it) } ?: dateString
            } catch (e: Exception) {
                dateString
            }
        }
    }
}

class TransactionDiffCallback : DiffUtil.ItemCallback<TransactionModel>() {

    override fun areItemsTheSame(oldItem: TransactionModel, newItem: TransactionModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TransactionModel, newItem: TransactionModel): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: TransactionModel, newItem: TransactionModel): Any? {
        return if (oldItem != newItem) {
            mutableMapOf<String, Any>().apply {
                if (oldItem.amount != newItem.amount) put("amount_changed", true)
                if (oldItem.name != newItem.name) put("name_changed", true)
                if (oldItem.date != newItem.date) put("date_changed", true)
                if (oldItem.type != newItem.type) put("type_changed", true)
            }
        } else {
            super.getChangePayload(oldItem, newItem)
        }
    }
}