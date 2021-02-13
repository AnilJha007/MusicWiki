package com.mobile.musicwiki.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

@Suppress("DEPRECATION")
fun TextView.setTextAppearanceV2(context: Context, resId: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        setTextAppearance(context, resId)
    } else {
        setTextAppearance(resId)
    }
}

fun View.snackBar(text: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    return Snackbar.make(this, text, duration).apply { show() }
}

fun TextView.setTextOrHide(text: String?, isHtml: Boolean = false) {
    text?.let {
        setText(
            if (isHtml) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    Html.fromHtml(it)
                }
            } else it
        )
        show()
    } ?: hide()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}