package com.example.stockdetails.domain.repository

import com.example.stockdetails.domain.model.CompanyInfo
import com.example.stockdetails.domain.model.CompanyListing
import com.example.stockdetails.domain.model.IntraDayInfo
import com.example.stockdetails.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepo {

    suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntraDayInfo(
        symbol: String
    ):Resource<List<IntraDayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ):Resource<CompanyInfo>

}