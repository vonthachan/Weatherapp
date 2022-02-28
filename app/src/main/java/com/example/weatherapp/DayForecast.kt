package com.example.weatherapp

import com.squareup.moshi.Json

data class DayForecast(
    @Json(name = "dt") val date: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: ForecastTemp,
    val pressure: Float,
    val humidity: Int,
    val weather: List<WeatherCondition>
)
