package com.example.scanitdemo.extensions

import com.scan.scanit.base.view.BaseFragment
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Ara Hakobyan on 8/11/2020.
 * Company IDT
 */

@SuppressLint("ObsoleteSdkInt")
inline fun <reified T : Any> Activity.launchActivityForResult(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

@SuppressLint("ObsoleteSdkInt")
inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

inline fun <reified T : Any> BaseFragment<*, *>.launchActivity(options: Bundle? = null, noinline init: Intent.() -> Unit = {}) {
    baseActivity?.launchActivity<T>(options, init)
}

inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

fun BaseFragment<*, *>.hideKeyboard() {
    baseActivity?.hideKeyboard()
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus)
}

fun Activity.showKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Activity.hideKeyboard(view: View?) {

    val baseView = this.findViewById<View>(android.R.id.content)
    view?.clearFocus()
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken ?: baseView.windowToken, 0)

}
