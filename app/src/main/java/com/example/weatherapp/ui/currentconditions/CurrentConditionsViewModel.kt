package com.example.weatherapp.ui.currentconditions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.CurrentConditions
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CurrentConditionsViewModel @javax.inject.Inject constructor() :
    ViewModel() {
    private val _currentConditions = MutableLiveData<CurrentConditions>()
    val currentConditions: LiveData<CurrentConditions>
        get() = _currentConditions

    fun loadData() = runBlocking {
        launch { _currentConditions.value = currentConditions.value }
    }
}