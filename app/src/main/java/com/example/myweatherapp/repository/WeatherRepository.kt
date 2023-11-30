package com.example.myweatherapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myweatherapp.models.WeatherDataClass
import com.example.myweatherapp.retrofit.RetrofitClient
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

   private var weatherData :MutableLiveData<WeatherDataClass> = MutableLiveData<WeatherDataClass>()
    val units = "metric"
   private val apiKey = "dfa1b224928489944e9b09e65e5ade1a"
    var isLoading :MutableLiveData<Boolean> = MutableLiveData()
    var errorCode :MutableLiveData<String> = MutableLiveData()
    var showMsg :MutableLiveData<Boolean> = MutableLiveData()
    init {
        isLoading.value = false
        showMsg.value = false
//        errorCode.value = ""

    }

    fun weatherData (city:String): MutableLiveData<WeatherDataClass>{
        val call = RetrofitClient.apiMethod.apiMethod(city,apiKey,units)

        call.enqueue(object :Callback<WeatherDataClass>{
            override fun onResponse(
                call: Call<WeatherDataClass>,
                response: Response<WeatherDataClass>
            ) {
                if (response.isSuccessful){
                    weatherData.value = response.body()
                    isLoading.value = false
                }else{
                    showMsg.value = true
                    errorCode.value = "${response.code()} ${response.message()}"
                    Log.d("failure", "onResponse: ${response.code()} ")
                    isLoading.value = false
                }
            }

            override fun onFailure(call: Call<WeatherDataClass>, t: Throwable) {
                isLoading.value = false
                showMsg.value = true
                errorCode.value = "${t.cause} ${t.message}"
            }

        })
      return weatherData
    }

    fun weatherByLoc (lat:Double,long:Double): MutableLiveData<WeatherDataClass>{
        val call = RetrofitClient.apiMethod.apiMethodByLoc(lat,long,apiKey,units)

        call.enqueue(object :Callback<WeatherDataClass>{
            override fun onResponse(
                call: Call<WeatherDataClass>,
                response: Response<WeatherDataClass>
            ) {
                if (response.isSuccessful){
                    weatherData.value = response.body()
                    isLoading.value = false
                }else{
                    showMsg.value = true
                    errorCode.value = "${response.code()} ${response.message()}"
                    Log.d("failure", "onResponse: ${response.code()} ")
                    isLoading.value = false
                }
            }

            override fun onFailure(call: Call<WeatherDataClass>, t: Throwable) {
                isLoading.value = false
                showMsg.value = true
                errorCode.value = "${t.cause} ${t.message}"
            }

        })
        return weatherData
    }
}