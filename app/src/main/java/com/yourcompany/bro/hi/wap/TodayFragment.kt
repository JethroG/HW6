package com.yourcompany.bro.hi.wap


import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.yourcompany.bro.hi.wap.R.id.txt_geo_coord
import com.yourcompany.bro.hi.wap.common.Common
import com.yourcompany.bro.hi.wap.m.WeatherForecast
import com.yourcompany.bro.hi.wap.m.WeatherResualt
import com.yourcompany.bro.hi.wap.retrofit.IOWMap
import com.yourcompany.bro.hi.wap.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class TodayFragment : Fragment() {

    private lateinit var imf_weather: ImageView
    internal lateinit var txt_city_name: TextView
    internal lateinit var txt_humidity: TextView
    internal lateinit var txt_sunrise: TextView
    private lateinit var txt_sunset: TextView
    private lateinit var txt_pressure: TextView
    private lateinit var txt_temperature: TextView
    private lateinit var txt_date: TextView
    private lateinit var txt_descriptiontxt: TextView
    private lateinit var wind: TextView
    private lateinit var geo: TextView
    private lateinit var weather_panel: LinearLayout
    private lateinit var progressBar: ProgressBar


    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var iowMap: IOWMap

    init {
        val retrofit = RetrofitClient.getInstance()
        iowMap = retrofit.create(IOWMap::class.java)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_today, container, false)
        imf_weather = view.findViewById(R.id.img_weather)
        txt_city_name = view.findViewById(R.id.txt_city_name)
        txt_humidity = view.findViewById(R.id.txt_humidity)
        txt_sunrise = view.findViewById(R.id.txt_sunrise)
        txt_sunset = view.findViewById(R.id.txt_set)
        txt_pressure = view.findViewById(R.id.txt_wpressu)
        txt_temperature = view.findViewById(R.id.txt_temp)
        txt_date = view.findViewById(R.id.txt_data_time)
        txt_descriptiontxt = view.findViewById(R.id.txt_description)
        wind = view.findViewById(R.id.txt_wind)
        geo = view.findViewById(R.id.txt_geo)


        weather_panel = view.findViewById(R.id.weater_panel)
        progressBar = view.findViewById(R.id.progress_bar)

        getWeatherInfprmation()




        return view
    }

    @SuppressLint("SetTextI18n")
    private fun getWeatherInfprmation() {
        compositeDisposable.add(iowMap.getWeatherByLatLng(
                Objects.requireNonNull<Location>(Common.current_Location).getLatitude().toString(),
                Common.current_Location!!.longitude.toString(),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ weatherResualt ->displyWeather(weatherResualt) }, { throwable -> Log.i("LOG", throwable.message) }))
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    @SuppressLint("SetTextI18n")
    private fun displyWeather(weatherResualt: WeatherResualt) {

        // Picasso.get().load(StringBuilder("https://openweathermap.org/img/w/")
//                            .append(weatherResualt.weather!![0].iconid)
//                            .append(".png").toString()).into(imf_weather)
        txt_city_name.text = weatherResualt.name
        txt_descriptiontxt.text = "Weather in " + weatherResualt.name
        txt_temperature.text = weatherResualt.main!!.temp.toString() + "°C"
        txt_date.setText(Common.takeData(weatherResualt.dt.toLong()))
        txt_pressure.text = weatherResualt.main!!.pressure.toString() + " hpa"
        txt_humidity.text = weatherResualt.main!!.humidity.toString() + " %"
        txt_sunrise.text = Common.convertHout(weatherResualt.sys!!.sunrise.toLong().toInt())
        txt_sunset.text = Common.convertHout(weatherResualt.sys!!.sunset.toLong().toInt())
        geo.text = "[ " + weatherResualt.coord.toString() + " ]"


        weather_panel.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        internal var instanse: TodayFragment? = null

        fun getInstanse(): TodayFragment {

            if (instanse == null)
                instanse = TodayFragment()

            return instanse as TodayFragment
        }
    }


}
