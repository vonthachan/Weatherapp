package com.example.weatherapp

import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ForecastActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var api: Api
    private lateinit var adapterData: List<DayForecast>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pro.openweathermap.org/data/2.5/forecast/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        api = retrofit.create(Api::class.java)

        setContentView(R.layout.activity_forecast)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)


    }

    override fun onResume() {
        super.onResume()
        println("onResume ForecastActivity")
        val call: Call<Forecast> = api.getForecast("55124")
        call.enqueue(object : Callback<Forecast> {
            override fun onResponse(
                call: Call<Forecast>,
                response: Response<Forecast>
            ) {
                val forecastConditions = response.body()
                forecastConditions?.let {
                    bindData(it.list)
                }

            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun bindData(forecast: List<DayForecast>) {
        adapterData = forecast
        recyclerView.adapter = MyAdapter(adapterData)
    }
}