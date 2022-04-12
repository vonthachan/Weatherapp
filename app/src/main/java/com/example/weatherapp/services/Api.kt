package com.example.weatherapp.services

import com.example.weatherapp.data.CurrentConditions
import com.example.weatherapp.data.Forecast
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    suspend fun getCurrentConditions(
        @Query("zip") zip: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = "551b6ce02d6310ed44c714d754e5d157",
    ): CurrentConditions

    @GET("weather")
    suspend fun getLocationCurrentConditions(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = "551b6ce02d6310ed44c714d754e5d157",
    ): CurrentConditions

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("zip") zip: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = "551b6ce02d6310ed44c714d754e5d157",
        @Query("cnt") count: String = "16"
    ): Forecast

    @GET("forecast/daily")
    suspend fun getLocationForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = "551b6ce02d6310ed44c714d754e5d157",
        @Query("cnt") count: String = "16"
    ): Forecast

}