package com.example.stockdetails.presentation.company_info

import com.example.stockdetails.domain.model.CompanyInfo
import com.example.stockdetails.domain.model.IntraDayInfo

data class CompanyInfoState(
    val stockInfo: List<IntraDayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
