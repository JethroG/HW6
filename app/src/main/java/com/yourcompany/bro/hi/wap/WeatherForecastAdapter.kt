package com.yourcompany.bro.hi.wap

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.yourcompany.bro.hi.wap.common.Common
import com.yourcompany.bro.hi.wap.m.WeatherForecast

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class WeatherForecastAdapter(private var context: Context, private var weatherForecast: WeatherForecast)
                                                : RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

//        Picasso.get().load(StringBuilder("https://openweathermap.org/img/w/")
//                .append(weatherForecast.list!![position])
//                .append(".png").toString()).into(holder.img_weather)

        holder.txt_data_time.text = StringBuilder(Common.convertHout(weatherForecast.list!![position].dt))
        holder.txt_description.text = StringBuilder(weatherForecast.list!![position].weather!![0].description!!)
        holder.txt_temperature.text = StringBuilder(weatherForecast.list!![position].main!!.temp.toString()).append("°C")


    }

    override fun getItemCount(): Int {
        return weatherForecast.list!!.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var txt_data_time: TextView
        internal var txt_description: TextView
        internal var txt_temperature: TextView
        internal var img_weather: ImageView

        init {

            img_weather = itemView.findViewById(R.id.img_weather1)
            txt_data_time = itemView.findViewById(R.id.txt_data)
            txt_description = itemView.findViewById(R.id.txt_description1)
            txt_temperature = itemView.findViewById(R.id.txt_temp)
        }
    }
}
