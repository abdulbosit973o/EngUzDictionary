package com.example.dictionaryapp.utils.extensions.extentions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.enguzdictionary.R


fun FragmentActivity.addFragment(fm: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .add(R.id.drawer_layout, fm)
        .commit()
}