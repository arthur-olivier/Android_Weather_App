package com.example.roomyweather.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repo: FiveDayForecastEntity)

    @Delete
    suspend fun delete(repo: FiveDayForecastEntity)


    @Query("SELECT * FROM FiveDayForecastEntity")
    fun getAllRepos(): Flow<List<FiveDayForecastEntity>>

    @Query("SELECT * FROM FiveDayForecastEntity WHERE city = :city LIMIT 1")
    fun getRepoByName(city: String): Flow<FiveDayForecastEntity?>

}