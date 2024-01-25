package com.example.enguzdictionary.utils.extensions.string_extensions

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

fun String.createSpannableText(query:String) : SpannableString {
    val spannable = SpannableString(this)
    val startIndex = this.indexOf(query)
    val endIndex = startIndex + query.length
    if (startIndex < 0 || endIndex > this.length) return spannable
    spannable.setSpan(
        ForegroundColorSpan(Color.RED),
        startIndex, // start
        endIndex, // end
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
    return spannable
}