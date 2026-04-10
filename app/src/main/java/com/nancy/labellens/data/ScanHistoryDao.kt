package com.nancy.labellens.data


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanHistoryDao {
    @Insert
    suspend fun insert(history: ScanHistoryEntity)

    @Query("SELECT * FROM scan_history ORDER BY timestamp DESC")
    fun getAllHistories(): Flow<List<ScanHistoryEntity>>

    @Query("DELETE FROM scan_history")
    suspend fun clearHistory()
}
