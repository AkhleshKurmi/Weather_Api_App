package com.example.myweatherapp.activities

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.R
import com.example.myweatherapp.databinding.ActivityMainBinding
import com.example.myweatherapp.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(),LocationListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: WeatherViewModel
    private var iconS = "https://openweathermap.org/img/wn/"
    private var iconE = "@2x.png"
    private lateinit var locationManager: LocationManager
    var lat = 0.0
    var long = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        binding.btnSearch.setOnClickListener {
            if (binding.etCity.text!!.isNotEmpty()) {
                weatherData(binding.etCity.text.toString())
                viewModel.repository.isLoading.value = true
                binding.etCity.text!!.clear()
            }else{
                binding.etCity.error = "Please enter a City"
            }
        }

        binding.btnLocation.setOnClickListener {
            getLocation()
            viewModel.repository.isLoading.value = true

        }

        viewModel.isLoading.observe(this){
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        viewModel.showMsg.observe(this){
            if (it){
                toastMsg()

            }
        }

    }

    private fun weatherData(city : String){

        viewModel.weatherData(city).observe(this) {
            val imageId = it.weather[0].icon

            binding.humadity.text = "Humidity ${it.main.humidity}"
            when (it.weather[0].main ){
                "Clouds" -> Picasso.get().load(R.drawable.clouds).into(binding.imgWeather)
                "Haze" ->   Picasso.get().load(R.drawable.haze).into(binding.imgWeather)
                "Clear" -> Picasso.get().load(R.drawable.clear).into(binding.imgWeather)
                "Drizzle" -> Picasso.get().load(R.drawable.drizzle).into(binding.imgWeather)
                "Rain"  ->Picasso.get().load(R.drawable.rain).into(binding.imgWeather)
                "Mist" ->Picasso.get().load(R.drawable.mist).into(binding.imgWeather)
                "Snow" ->Picasso.get().load(R.drawable.snow).into(binding.imgWeather)
            }
            Picasso.get().load(R.drawable.humidity).into(binding.imgHumadity)
            binding.imgHumadity.visibility =View.VISIBLE
            binding.imgWind.visibility = View.VISIBLE
            Picasso.get().load(R.drawable.wind).into(binding.imgWind)
            binding.tvWindSpeed.text = "Wind Speed - \n${Math.round(it.wind.speed*3.6)} km/h"
            var temp = it.main.temp.roundToInt()
            binding.tvTemp.text = "Temp - $temp °C"
            binding.tvCity.text = it.name
            binding.tvWeather.text = it.weather[0].main
        }

    }

    private fun weatherByLocation(lat:Double,long:Double){
        viewModel.weatherByLoc(lat,long).observe(this) {
            val imageId = it.weather[0].icon

            binding.humadity.text = "Humidity ${it.main.humidity}"
            when (it.weather[0].main ){
                "Clouds" -> Picasso.get().load(R.drawable.clouds).into(binding.imgWeather)
                "Haze" ->   Picasso.get().load(R.drawable.haze).into(binding.imgWeather)
                "Clear" -> Picasso.get().load(R.drawable.clear).into(binding.imgWeather)
                "Drizzle" -> Picasso.get().load(R.drawable.drizzle).into(binding.imgWeather)
                "Rain"  ->Picasso.get().load(R.drawable.rain).into(binding.imgWeather)
                "Mist" ->Picasso.get().load(R.drawable.mist).into(binding.imgWeather)
                "Snow" ->Picasso.get().load(R.drawable.snow).into(binding.imgWeather)
            }
            Picasso.get().load(R.drawable.humidity).into(binding.imgHumadity)
            binding.imgHumadity.visibility =View.VISIBLE
            binding.imgWind.visibility = View.VISIBLE
            Picasso.get().load(R.drawable.wind).into(binding.imgWind)
            binding.tvWindSpeed.text = "Wind Speed - \n${Math.round(it.wind.speed*3.6)} km/h"
            var temp = it.main.temp.roundToInt()
            binding.tvTemp.text = "Temp - $temp °C"
            binding.tvCity.text = it.name
            binding.tvWeather.text = it.weather[0].main
        }

    }

   private fun toastMsg(){
       viewModel.errorCode.observe(this){
           viewModel.repository.showMsg.value = false
           Toast.makeText(this, "Error $it", Toast.LENGTH_LONG).show()
       }
   }

    override fun onLocationChanged(location: Location) {
        lat = location.latitude
        long = location.longitude
    }
    private fun getLocation(){
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1001)
        }else{
            weatherByLocation(lat,long)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5f,this)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1001)

            }
        }
    }
}
