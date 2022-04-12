package com.example.weatherapp.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.CurrentConditions
import com.example.weatherapp.services.Api
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val service: Api) : ViewModel() {

    private var latitude: Double? = null
    private var longitude: Double? = null
    private var zipCode: String? = null
    private val _enableButton = MutableLiveData(false)
    private val _showErrorDialog = MutableLiveData(false)
    private val _currentConditions = MutableLiveData<CurrentConditions>()

    val currentConditions: LiveData<CurrentConditions>
        get() = _currentConditions
    val showErrorDialog: LiveData<Boolean>
        get() = _showErrorDialog
    val enableButton: LiveData<Boolean>
        get() = _enableButton


    fun getZipCode(): String? {
        return zipCode
    }

    fun getLat(): String? {
        return latitude.toString()
    }

    fun getLon(): String? {
        return longitude.toString()
    }

    //Updates zipCode and if isValid, enableButton = true
    fun updateZipCode(zipCode: String) {
        if (zipCode != this.zipCode) {
            this.zipCode = zipCode
            _enableButton.value = isValidZipCode(zipCode)
        }
    }

    //Checks for valid zipCode
    private fun isValidZipCode(zipCode: String): Boolean {
        return zipCode.length == 5 && zipCode.all { it.isDigit() }
    }

    fun updateCoordinates(lat: Double, lon: Double) {
        this.latitude = lat
        this.longitude = lon
    }

    fun submitButtonClicked() = runBlocking {
        zipCode.let {
            try {
                _currentConditions.value = service.getCurrentConditions(it!!)
            } catch (e: HttpException) {
                _showErrorDialog.value = true
            }
        }
        Log.d(SearchViewModel::class.simpleName, zipCode!!)
    }

    fun locationButtonClicked() = runBlocking {
        try {
            _currentConditions.value = latitude?.let {
                longitude?.let { it1 ->
                    service.getLocationCurrentConditions(
                        it,
                        it1
                    )
                }
            }
        } catch (e: HttpException) {
            _showErrorDialog.value = true
        }

    }
}