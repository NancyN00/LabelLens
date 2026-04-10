package com.nancy.labellens.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ScanHistoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val scanHistoryDao: ScanHistoryDao
}
