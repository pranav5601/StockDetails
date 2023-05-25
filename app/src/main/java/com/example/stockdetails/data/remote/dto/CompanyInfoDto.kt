package com.example.stockdetails.data.remote.dto

import com.squareup.moshi.Json

data class CompanyInfoDto(
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "Description")
    val description: String?,
    @field:Json(name = "Symbol")
    val symbol: String?,
    @field:Json(name = "Country")
    val country: String?,
    @field:Json(name = "Industry")
    val industry: String?,

)