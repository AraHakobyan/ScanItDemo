package com.example.scanitdemo.extensions

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by Ara Hakobyan on 8/12/2020.
 * Company IDT
 */
@SuppressLint("ObsoleteSdkInt")
inline fun <reified T : Any> Fragment.launchActivityForResult(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this.requireContext())
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}