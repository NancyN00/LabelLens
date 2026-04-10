package com.nancy.labellens.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_history")
data class ScanHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val scanType: String,
    val resultText: String,
    val timestamp: Long = System.currentTimeMillis()
)
