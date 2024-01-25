package com.example.enguzdictionary.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.enguzdictionary.R
import com.example.enguzdictionary.data.local.MySharedPref
import com.example.enguzdictionary.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val drawerLayout: DrawerLayout = binding.drawerLayout

//        binding.appBarMain.searchVoiceBtn.setOnClickListener {
//            Toast.makeText(this, "salom", Toast.LENGTH_SHORT).show()
//        }

        setSupportActionBar(binding.appBarMain.toolbar)

//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }


        when(MySharedPref.getOpenScreen()) {
            0 -> {

            }
            else -> {

            }
        }


        val navView: NavigationView = binding.navView

        navView.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId) {
                    R.id.bookmarks -> {
                        Log.d("TTT", "bosildiim")
                        Toast.makeText(this@MainActivity, "Bookmarks", Toast.LENGTH_SHORT).show()
                        return true
                    }
                    R.id.share -> {
                        Toast.makeText(this@MainActivity, "Share", Toast.LENGTH_SHORT).show()
                        return true
                    }
                    R.id.like -> {
                        Toast.makeText(this@MainActivity, "rate", Toast.LENGTH_SHORT).show()
                        return true
                    }
                    R.id.more_apps -> {
                        Toast.makeText(this@MainActivity, "More apps", Toast.LENGTH_SHORT).show()
                        return true
                    }
                    R.id.privacy_police -> {
                        Toast.makeText(this@MainActivity, "Privacy", Toast.LENGTH_SHORT).show()
                        return true
                    }
                }
                return false
            }

        })

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_change_uz, R.id.bookmarks, R.id.share
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}