package com.example.myweatherapp.retrofit

import com.example.myweatherapp.models.ApiMethods
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {

    private val retrofitClient :Retrofit.Builder by lazy {
        Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
    }

    val apiMethod :ApiMethods by lazy {
        retrofitClient.build().create(ApiMethods::class.java)
    }


}