package com.example.weatherapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forecast(
    val list: List<DayForecast>
) : Parcelable
