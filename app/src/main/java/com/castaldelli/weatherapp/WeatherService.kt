package com.castaldelli.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("current.json")
    open fun getCurrentWeather(
        @Query("key") key:String = "8102b27b813e4955ae1140551210508",
        @Query("q") q:String,
        @Query("aqi") aqi:String = "no"
    ): Call<CurrentWeather>




//http://api.weatherapi.com/v1/current.json



    // ?key=8102b27b813e4955ae1140551210508
// &q=London
// &aqi=no

//http://api.weatherapi.com/v1/forecast.json



    // ?key=8102b27b813e4955ae1140551210508
// &q=London
// &days=1
// &aqi=no
// &alerts=no

}