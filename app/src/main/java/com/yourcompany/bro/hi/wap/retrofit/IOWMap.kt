package com.yourcompany.bro.hi.wap.retrofit


import com.yourcompany.bro.hi.wap.m.WeatherForecast
import com.yourcompany.bro.hi.wap.m.WeatherResualt

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IOWMap {

    @GET("weather")
    fun getWeatherByLatLng(@Query("lat") lat: String,
                           @Query("lon") lng: String,
                           @Query("appid") appid: String,
                           @Query("units") unit: String): Observable<WeatherResualt>


    @GET("forecast")
    fun getForecastWeatherByLatLng(
                            @Query("lat") lat: String,
                            @Query("lon") lng: String,
                            @Query("appid") appid: String,
                            @Query("units") unit: String): Observable<WeatherForecast>
}
