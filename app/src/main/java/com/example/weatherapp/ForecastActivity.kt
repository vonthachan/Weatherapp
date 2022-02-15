package com.example.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ForecastActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private val forecastTempData = listOf<ForecastTemp>(
        ForecastTemp(31f, 13f, 35f),
        ForecastTemp(40f, 31f, 42f),
        ForecastTemp(30f, 8f, 33f),
        ForecastTemp(27f, 24f, 27f),
        ForecastTemp(35f, -1f, 36f),
        ForecastTemp(7f, 0f, 7f),
        ForecastTemp(20f, 4f, 22f),
        ForecastTemp(21f, 11f, 26f),
        ForecastTemp(28f, 14f, 32f),
        ForecastTemp(22f, 7f, 25f),
        ForecastTemp(19f, 11f, 22f),
        ForecastTemp(26f, 15f, 29f),
        ForecastTemp(25f, 11f, 27f),
        ForecastTemp(27f, 10f, 30f),
        ForecastTemp(30f, 18f, 33f),
        ForecastTemp(35f, 13f, 37f),
    )

    private val adapterData = listOf<DayForecast>(
        DayForecast(1644273472, 1644214904, 1644262244, forecastTempData[0], 1000f, 30),
        DayForecast(1644359872, 1644300224, 1644348164, forecastTempData[1], 1000f, 30),
        DayForecast(1644446272, 1644391004, 1644437804, forecastTempData[2], 1000f, 30),
        DayForecast(1644532672, 1644475664, 1644522224, forecastTempData[3], 1000f, 30),
        DayForecast(1644619072, 1644562244, 1644605624, forecastTempData[4], 1000f, 30),
        DayForecast(1644705472, 1644649604, 1644692324, forecastTempData[5], 1000f, 30),
        DayForecast(1644791872, 1644735764, 1644782564, forecastTempData[6], 1000f, 30),
        DayForecast(1644878272, 1644819044, 1644868004, forecastTempData[7], 1000f, 30),
        DayForecast(1644964672, 1644905744, 1644952544, forecastTempData[8], 1000f, 30),
        DayForecast(1645051072, 1644992504, 1645039304, forecastTempData[9], 1000f, 30),
        DayForecast(1645137472, 1645079204, 1645126064, forecastTempData[10], 1000f, 30),
        DayForecast(1645223872, 1645166564, 1645213664, forecastTempData[11], 1000f, 30),
        DayForecast(1645310272, 1645252724, 1645299284, forecastTempData[12], 1000f, 30),
        DayForecast(1645396672, 1645386464, 1645383404, forecastTempData[13], 1000f, 30),
        DayForecast(1645483072, 1645423844, 1645470764, forecastTempData[14], 1000f, 30),
        DayForecast(1645569472, 1645511384, 1645557704, forecastTempData[15], 1000f, 30),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = MyAdapter(adapterData)

//        forecastTempData.get(0)
    }
}