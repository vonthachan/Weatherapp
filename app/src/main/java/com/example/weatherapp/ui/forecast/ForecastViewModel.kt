package com.example.weatherapp.ui.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.Forecast
import com.example.weatherapp.services.Api
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val service: Api) : ViewModel() {
    private val _forecast = MutableLiveData<Forecast>()
    val forecast: LiveData<Forecast>
        get() = _forecast

    fun loadData(zipCode: String) = runBlocking {
        launch { _forecast.value = service.getForecast(zipCode) }
    }

    fun loadData(lat: Double, lon: Double) = runBlocking {
        launch { _forecast.value = service.getLocationForecast(lat, lon) }
    }
}