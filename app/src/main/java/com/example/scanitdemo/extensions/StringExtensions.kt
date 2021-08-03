package com.example.scanitdemo.extensions

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ara Hakobyan on 8/13/2020.
 * Company IDT
 */
fun String.isPermissionAdded()= this.contains("+")

fun String.toDateFormat(): String{
    if (this.length > 10){
        return this.substring(0, 10)
    }
    return this
}

@SuppressLint("SimpleDateFormat")
fun String.dmy(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date: Date? = sdf.parse(this.toDateFormat())
    val formatter: DateFormat = SimpleDateFormat("dd-MM-yyyy")
    if (date != null){
        return formatter.format(date)?: this
    }
    return this
}

fun String?.toValueOrNull(): String? {
    if (this == null) return null
    else return "\'$this\'"
}