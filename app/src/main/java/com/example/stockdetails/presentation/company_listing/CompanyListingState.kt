package com.example.stockdetails.presentation.company_listing

import com.example.stockdetails.domain.model.CompanyListing
import com.example.stockdetails.util.Resource

data class CompanyListingState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
