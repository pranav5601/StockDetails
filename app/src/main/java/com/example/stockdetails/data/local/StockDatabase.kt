package com.example.stockdetails.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stockdetails.domain.model.CompanyListing

@Database(entities = [CompanyListingEntity::class],
version = 1)
abstract class StockDatabase: RoomDatabase() {
    abstract val dao: StockDao
}