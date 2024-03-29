package com.example.stockdetails.data.csv

import com.example.stockdetails.data.mapper.toIntraDayInfo
import com.example.stockdetails.data.remote.dto.IntraDayInfoDto
import com.example.stockdetails.domain.model.IntraDayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject

class IntraDayListingParser @Inject constructor() : CSVParser<IntraDayInfo> {

    override suspend fun parse(stream: InputStream): List<IntraDayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader.readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?:return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = IntraDayInfoDto(timestamp = timestamp, close.toDouble())
                        dto.toIntraDayInfo()

                }
                .filter {
                    it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
                }
                .also {
                    csvReader.close()
                }
        }
    }
}