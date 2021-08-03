package com.example.scanitdemo.extensions

import android.view.View

/**
 * Created by Ara Hakobyan on 8/13/2020.
 * Company IDT
 */
fun Boolean.toVisibility(): Int = if (this) {
    View.VISIBLE
} else {
    View.GONE
}