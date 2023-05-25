package com.example.stockdetails.data.repository

import com.example.stockdetails.data.csv.CSVParser
import com.example.stockdetails.data.local.StockDatabase
import com.example.stockdetails.data.mapper.toCompanyInfo
import com.example.stockdetails.data.mapper.toCompanyListing
import com.example.stockdetails.data.mapper.toCompanyListingEntity
import com.example.stockdetails.data.remote.StockApi
import com.example.stockdetails.domain.model.CompanyInfo
import com.example.stockdetails.domain.model.CompanyListing
import com.example.stockdetails.domain.model.IntraDayInfo
import com.example.stockdetails.domain.repository.StockRepo
import com.example.stockdetails.util.Resource
import com.opencsv.CSVReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepoImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intraDayInfoParser: CSVParser<IntraDayInfo>
) : StockRepo {
    private val dao = db.dao
    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListing = dao.searchCompanyList(query)
            emit(Resource.Success(
                data = localListing.map { it.toCompanyListing() }
            ))
            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val fromCache = !isDbEmpty && !fetchFromRemote
            if (fromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListing = try {
                val response = api.greetings()
                companyListingParser.parse(response.byteStream())
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListing?.let { listing ->
                dao.removeCompanyListing()
                dao.insertCompanyListing(
                    listing.map { it.toCompanyListingEntity() }
                )
                emit(
                    Resource.Success(
                        data = dao.searchCompanyList("").map { it.toCompanyListing() })
                )
                emit(Resource.Loading(false))

            }
        }
    }

    override suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>> {
        return try {
            val res = api.getIntraDayInfo(symbol)
            val result = intraDayInfoParser.parse(res.byteStream())
            Resource.Success(result)

        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = e.message
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = e.message()
            )
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val res = api.getCompanyInfo(symbol)
            Resource.Success(res.toCompanyInfo())

        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Couldn't load the Company data!")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Couldn't load the Company data!")
        }
    }

}