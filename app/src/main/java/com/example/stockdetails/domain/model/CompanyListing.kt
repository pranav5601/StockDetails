package com.example.stockdetails.domain.model

data class CompanyListing(
    val symbol: String,
    val name: String,
    val exchange: String,
    val asset_type: String,
)