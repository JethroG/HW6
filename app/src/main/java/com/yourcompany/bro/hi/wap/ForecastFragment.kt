package com.yourcompany.bro.hi.wap


import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.yourcompany.bro.hi.wap.common.Common
import com.yourcompany.bro.hi.wap.m.WeatherForecast
import com.yourcompany.bro.hi.wap.retrofit.IOWMap
import com.yourcompany.bro.hi.wap.retrofit.RetrofitClient

import java.util.Objects


import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


/**
 * A simple [Fragment] subclass.
 */
class ForecastFragment : Fragment() {


    internal var compositeDisposable: CompositeDisposable = CompositeDisposable()
    internal var iowMap: IOWMap

    internal lateinit var txt_city_name: TextView
    internal lateinit var txt_geo_coord: TextView
    internal lateinit var recycler_forecast: RecyclerView

    init {
        val retrofit = RetrofitClient.getInstance()
        iowMap = retrofit.create(IOWMap::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val itemView = inflater.inflate(R.layout.fragment_forecast, container, false)

        txt_city_name = itemView.findViewById(R.id.txt_city_name)
        txt_geo_coord = itemView.findViewById(R.id.txt_geo_coord)

        recycler_forecast = itemView.findViewById(R.id.recycle_forecast)
        recycler_forecast.setHasFixedSize(true)
        recycler_forecast.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        getForecasrWeatherInfo()
        return itemView
    }

    private fun getForecasrWeatherInfo() {
        compositeDisposable.add(iowMap.getForecastWeatherByLatLng(
                Objects.requireNonNull<Location>(Common.current_Location).getLatitude().toString(),
                Common.current_Location!!.longitude.toString(),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ weatherForecast -> displyForecstWeather(weatherForecast) }, { throwable -> Log.i("LOG", throwable.message) }))
    }

    private fun displyForecstWeather(weatherForecast: WeatherForecast) {
        txt_city_name.text = StringBuilder(weatherForecast.city!!.name!!)
        txt_geo_coord.text = StringBuilder(weatherForecast.city!!.coord!!.toString())

        val weatherForecastAdapter = WeatherForecastAdapter(this.context!!, weatherForecast)
        recycler_forecast.adapter = weatherForecastAdapter

    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        internal var instanse: ForecastFragment? = null

        fun getInstanse(): ForecastFragment {

            if (instanse == null)
                instanse = ForecastFragment()
            return instanse as ForecastFragment
        }
    }
}


