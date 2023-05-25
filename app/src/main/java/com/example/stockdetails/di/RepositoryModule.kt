package com.example.stockdetails.di

import com.example.stockdetails.data.csv.CSVParser
import com.example.stockdetails.data.csv.CompanyListingParser
import com.example.stockdetails.data.csv.IntraDayListingParser
import com.example.stockdetails.data.repository.StockRepoImpl
import com.example.stockdetails.domain.model.CompanyListing
import com.example.stockdetails.domain.model.IntraDayInfo
import com.example.stockdetails.domain.repository.StockRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>


    @Binds
    @Singleton
    abstract fun intraDayInfoParser(
        intraDatInfoParser: IntraDayListingParser
    ): CSVParser<IntraDayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepoImpl: StockRepoImpl
    ): StockRepo
}