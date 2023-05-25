package com.example.stockdetails.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockdetails.domain.repository.StockRepo
import com.example.stockdetails.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val stockRepo: StockRepo
) : ViewModel() {


    var state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)
            val companyInfoResult = async {
                stockRepo.getCompanyInfo(symbol)
            }

            val intraDayInfoResult = async { stockRepo.getIntraDayInfo(symbol) }

            when (val res = companyInfoResult.await()){
                is Resource.Success -> {
                    state = state.copy(
                        company = res.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error ->{
                    state = state.copy(
                        isLoading = false,
                        error = res.message,
                        company = null
                    )
                }
                else -> Unit
            }

            when (val res = intraDayInfoResult.await()){
                is Resource.Success -> {
                    state = state.copy(
                        stockInfo = res.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error ->{
                    state = state.copy(
                        isLoading = false,
                        error = res.message,
                        company = null
                    )
                }
                else -> {
                    print("Intraday error${res.message}")
                }
            }

        }
    }
}