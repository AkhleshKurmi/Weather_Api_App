package com.example.myweatherapp.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiMethods {
//    @Headers("appid=dfa1b224928489944e9b09e65e5ade1a") ?units=metric&
    @GET("weather")
    fun apiMethod(@Query("q") city:String,
                  @Query("appid")apiKey:String,
                  @Query("units")units:String) : Call<WeatherDataClass>

    @GET("weather")
    fun apiMethodByLoc(@Query("lat") lat:Double,
                       @Query("lon")long:Double,
                  @Query("appid")apiKey:String,
                  @Query("units")units:String) : Call<WeatherDataClass>
}