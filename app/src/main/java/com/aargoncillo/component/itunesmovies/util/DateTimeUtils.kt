package com.aargoncillo.component.itunesmovies.util

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {

    @SuppressLint("SimpleDateFormat")
    fun getYear(inputDate: String?): String {
        return inputDate?.apply {
            val dateFormat = SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss'Z'")
            val date = dateFormat.parse(this) as Date
            val calendar  = Calendar.getInstance()
            calendar.time = date
            return "${calendar.get(Calendar.YEAR)}"
        } ?: ""
    }
}