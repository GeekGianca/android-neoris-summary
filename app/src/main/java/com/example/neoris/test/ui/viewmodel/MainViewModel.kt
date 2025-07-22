package com.example.neoris.test.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neoris.core.model.BalanceSummaryModel
import com.example.neoris.core.model.Record
import com.example.neoris.core.model.TransactionModel
import com.example.neoris.domain.GetBalanceSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSummaryBalanceSummaryUseCase: GetBalanceSummaryUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<SummaryUiState>(SummaryUiState.Loading(true))
    val uiState: StateFlow<SummaryUiState> = _uiState.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadBalanceSummary()
    }

    fun loadBalanceSummary() {
        viewModelScope.launch {
            getSummaryBalanceSummaryUseCase()
                .collect { result ->
                    when (result) {
                        is com.example.neoris.shared.result.Result.Loading -> {
                            _uiState.value = SummaryUiState.Loading(true)
                        }

                        is com.example.neoris.shared.result.Result.Success -> {
                            _uiState.value = SummaryUiState.Success(result.data.record)
                        }

                        is com.example.neoris.shared.result.Result.Error -> {
                            _uiState.value = SummaryUiState.Error(result.message!!.ifEmpty {
                                result.exception.message ?: "Error desconocido"
                            })
                            _error.value = result.message?.ifEmpty {
                                result.exception.message ?: "Error desconocido"
                            }
                        }

                        is com.example.neoris.shared.result.Result.Empty -> {
                            _uiState.value = SummaryUiState.Empty
                        }
                    }
                    _uiState.value = SummaryUiState.Loading(false)
                }
        }
    }

    fun retry() {
        loadBalanceSummary()
    }

    fun clearError() {
        _error.value = null
    }

}

sealed class SummaryUiState {
    data class Success(val data: Record) : SummaryUiState()
    data class Loading(val state: Boolean) : SummaryUiState()
    data class Error(val message: String) : SummaryUiState()
    data object Empty : SummaryUiState()
}