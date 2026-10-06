package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanDao {

    @Query("SELECT * FROM saved_scans ORDER BY scannedAt DESC")
    fun getAllScans(): Flow<List<SavedScanEntity>>

    @Query("SELECT * FROM saved_scans WHERE isFavorite = 1 ORDER BY scannedAt DESC")
    fun getFavoriteScans(): Flow<List<SavedScanEntity>>

    @Query("SELECT * FROM saved_scans WHERE id = :id LIMIT 1")
    suspend fun getScanById(id: String): SavedScanEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScan(scan: SavedScanEntity)

    @Update
    suspend fun updateScan(scan: SavedScanEntity)

    @Delete
    suspend fun deleteScan(scan: SavedScanEntity)

    @Query("DELETE FROM saved_scans WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM saved_scans")
    suspend fun clearAll()
}
