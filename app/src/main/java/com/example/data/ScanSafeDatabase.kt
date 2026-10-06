package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedScanEntity::class], version = 1, exportSchema = false)
abstract class ScanSafeDatabase : RoomDatabase() {

    abstract fun scanDao(): ScanDao

    companion object {
        @Volatile
        private var INSTANCE: ScanSafeDatabase? = null

        fun getDatabase(context: Context): ScanSafeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScanSafeDatabase::class.java,
                    "scansafe_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
