package com.example.weatherapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ForecastActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

//    val forecastTempData = listOf<ForecastTemp>(
//        ForecastTemp(31f, 13f, 35f),
//        ForecastTemp(40f, 31f, 42f),
//        ForecastTemp(30f, 8f, 33f),
//        ForecastTemp(27f, 24f, 27f),
//        ForecastTemp(35f, -1f, 36f),
//        ForecastTemp(7f, 0f, 7f),
//        ForecastTemp(20f, 4f, 22f),
//        ForecastTemp(21f, 11f, 26f),
//        ForecastTemp(28f, 14f, 32f),
//        ForecastTemp(22f, 7f, 25f),
//        ForecastTemp(19f, 11f, 22f),
//        ForecastTemp(26f, 15f, 29f),
//        ForecastTemp(25f, 11f, 27f),
//        ForecastTemp(27f, 10f, 30f),
//        ForecastTemp(30f, 18f, 33f),
//        ForecastTemp(35f, 13f, 37f),
//    )

    val adapterData = listOf<Data>(
        Data(1644273472),
        Data(1644359872),
        Data(1644446272),
        Data(1644532672),
        Data(1644619072),
        Data(1644705472),
        Data(1644791872),
        Data(1644878272),
        Data(1644964672),
        Data(1645051072),
        Data(1645137472),
        Data(1645223872),
        Data(1645310272),
        Data(1645396672),
        Data(1645483072),
        Data(1645569472),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = MyAdapter(adapterData)

    }
}