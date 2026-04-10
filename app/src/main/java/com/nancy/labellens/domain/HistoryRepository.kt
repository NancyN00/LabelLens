package com.nancy.labellens.domain

import com.nancy.labellens.data.ScanHistoryDao
import com.nancy.labellens.data.ScanHistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val dao: ScanHistoryDao
) {
    fun getHistory(): Flow<List<ScanHistoryEntity>> = dao.getAllHistories()

    suspend fun saveScan(type: String, result: String) {
        val entity = ScanHistoryEntity(scanType = type, resultText = result)
        dao.insert(entity)
    }

    suspend fun clearAll() = dao.clearHistory()
}