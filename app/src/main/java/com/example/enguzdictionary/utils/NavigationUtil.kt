package com.example.dictionaryapp.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.enguzdictionary.R

fun FragmentActivity.addScreen(fm : Fragment) {
    supportFragmentManager.beginTransaction()
        .add(R.id.drawer_layout, fm)
        .commit()
}

fun FragmentActivity.replaceScreen(fm : Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.drawer_layout, fm)
        .addToBackStack(fm::class.java.name)
        .commit()
}

fun FragmentActivity.replaceScreenWithoutSave(fm : Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.drawer_layout, fm)
        .commit()
}


fun FragmentActivity.popBackStack() {
    supportFragmentManager.popBackStack()
}

fun Fragment.replaceScreen(fm : Fragment) {
    requireActivity().replaceScreen(fm)
}

fun Fragment.replaceScreenWithoutSave(fm : Fragment) {
    requireActivity().replaceScreenWithoutSave(fm)
}

fun Fragment.popBackStack() {
    requireActivity().popBackStack()
}
