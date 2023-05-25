package com.example.stockdetails.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListingEntity(
    val symbol: String,
    val name: String,
    val exchange: String,
    val asset_type: String,
    @PrimaryKey val id: Int? = null


)