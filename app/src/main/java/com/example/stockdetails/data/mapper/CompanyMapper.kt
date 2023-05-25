package com.example.stockdetails.data.mapper

import com.example.stockdetails.data.local.CompanyListingEntity
import com.example.stockdetails.data.remote.dto.CompanyInfoDto
import com.example.stockdetails.domain.model.CompanyInfo
import com.example.stockdetails.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing{
    return CompanyListing(symbol = symbol,
    name = name,
    exchange = exchange,
    asset_type = asset_type)
}



fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity{
    return CompanyListingEntity(symbol = symbol,
    name = name,
    exchange = exchange,
    asset_type = asset_type,

    )


}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}