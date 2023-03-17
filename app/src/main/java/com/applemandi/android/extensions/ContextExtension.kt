package com.applemandi.android.view.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun Context.showToast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Context.showToast(@StringRes text: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}


