package com.example.stockdetails.data.csv

import com.example.stockdetails.domain.model.CompanyListing
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class CompanyListingParser @Inject constructor() : CSVParser<CompanyListing> {

    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader.readAll()
                .drop(1)
                .mapNotNull {line ->
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchange = line.getOrNull(2)
                    val assetType = line.getOrNull(3)

                    CompanyListing(symbol = symbol ?:return@mapNotNull null,
                        name = name ?:return@mapNotNull null,
                        exchange = exchange ?:return@mapNotNull null,
                        asset_type = assetType ?:return@mapNotNull null)

                }
                .also {
                    csvReader.close()
                }
        }
    }
}