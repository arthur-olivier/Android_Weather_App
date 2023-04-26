package com.example.roomyweather.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FiveDayForecastEntity::class], version = 5)
abstract class AppDatabase: RoomDatabase() {
    abstract fun ForecastDao(): ForecastDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "SavedForecastName2.db"
            ).fallbackToDestructiveMigration().fallbackToDestructiveMigrationOnDowngrade().build()

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }
}