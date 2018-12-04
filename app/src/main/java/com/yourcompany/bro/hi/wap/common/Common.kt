package com.yourcompany.bro.hi.wap.common

import android.annotation.SuppressLint
import android.location.Location

import java.text.SimpleDateFormat
import java.util.Date

object Common {
    val APP_ID = "3f5cc7504292bd80aa016ad41d21df69"
    var current_Location: Location? = null

    @SuppressLint("SimpleDateFormat")
    fun takeData(dt: Long): Int {
        val date = Date(dt * 1000L)
        val simpleDateFormat = SimpleDateFormat("HH:mm EEE MM yyyy")
        val form = simpleDateFormat.format(date)

        return Integer.parseInt(form)


    }

    @SuppressLint("SimpleDateFormat")
    fun convertHout(sunrise: Int): String? {
        val date = Date(sunrise * 1000L)
        val simpleDateFormat = SimpleDateFormat("HH:mm dd EEE MM yyyy")
        val form = simpleDateFormat.format(date)

        return form
    }
}
