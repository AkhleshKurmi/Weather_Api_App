package com.example.myweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweatherapp.models.WeatherDataClass
import com.example.myweatherapp.repository.WeatherRepository

class WeatherViewModel :ViewModel() {

    var repository = WeatherRepository()
    var isLoading :MutableLiveData<Boolean> = repository.isLoading
    var errorCode:MutableLiveData<String> = repository.errorCode
    var showMsg :MutableLiveData<Boolean> = repository.showMsg


    fun weatherData (city:String) : LiveData<WeatherDataClass>{
     return repository.weatherData(city)
    }
    fun weatherByLoc (lat:Double,long:Double) : LiveData<WeatherDataClass>{
        return repository.weatherByLoc(lat,long)
    }

}