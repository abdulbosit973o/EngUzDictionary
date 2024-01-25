package com.example.dictionaryapp.utils.extensions.viewGroupExtensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes resourceId: Int): View {
    return LayoutInflater.from(context).inflate(resourceId, this, false)
}