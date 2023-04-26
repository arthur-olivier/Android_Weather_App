package com.example.roomyweather.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
@Entity
data class FiveDayForecastEntity(
    @PrimaryKey
    val city: String,
    val timestamp: Long
)