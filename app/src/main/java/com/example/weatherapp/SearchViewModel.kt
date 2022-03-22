package com.example.weatherapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val service: Api) : ViewModel() {

    private var zipCode = ""
    private val _enableButton = MutableLiveData(false)
    private val _showErrorDialog = MutableLiveData(false)
    private val _currentConditions = MutableLiveData<CurrentConditions>()

    val currentConditions: LiveData<CurrentConditions>
        get() = _currentConditions
    val showErrorDialog: LiveData<Boolean>
        get() = _showErrorDialog
    val enableButton: LiveData<Boolean>
        get() = _enableButton

    fun getZipCode(): String {
        return zipCode
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

    fun submitButtonClicked() = runBlocking {
        zipCode.let {
            try {
                _currentConditions.value = service.getCurrentConditions(it)
            } catch (e: HttpException) {
                _showErrorDialog.value = true
            }
        }
        Log.d(SearchViewModel::class.simpleName, zipCode)
    }
}