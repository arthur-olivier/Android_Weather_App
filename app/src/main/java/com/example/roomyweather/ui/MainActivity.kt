package com.example.roomyweather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.roomyweather.R
import com.example.roomyweather.data.AppDatabase
import com.example.roomyweather.data.FiveDayForecastReposRepository
import com.example.roomyweather.data.FiveDayForecastRepository
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfig: AppBarConfiguration
    var drawerEntries = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment

        val navController = navHostFragment.navController
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        appBarConfig = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfig)

        findViewById<NavigationView>(R.id.nav_view)?.setupWithNavController(navController)

        //addEntriesToDrawer()

        val repository = FiveDayForecastReposRepository(
            AppDatabase.getInstance(application).ForecastDao()
        )

        val FiveDayRepo = repository.getAllBookmarkedRepos().asLiveData()

        val viewModel2: SavedDataRepoViewModel by viewModels()

        val navView: NavigationView = findViewById(R.id.nav_view)
        val entriesSubMenu = navView.menu.findItem(R.id.submenu_item).subMenu
        entriesSubMenu?.clear()

        val viewModel: FiveDayForecastViewModel by viewModels()

        val addedEntries = HashSet<String>()
        FiveDayRepo.observe(this, Observer { forecastList ->
            if (forecastList != null) {
                for (forecast in forecastList) {
                    drawerEntries.add(forecast.city)
                    Log.d("main", drawerEntries.toString())
                    if (!addedEntries.contains(forecast.city)) {
                        entriesSubMenu?.add(forecast.city)?.setOnMenuItemClickListener {
                            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
                            sharedPrefs.edit().putString("city",forecast.city).apply()
                            navController.navigate(R.id.Forecast_search)
                            drawerLayout.closeDrawer(GravityCompat.START)
                            true
                        }
                        addedEntries.add(forecast.city)
                    }
                }
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_container)
        return navController.navigateUp(appBarConfig)
                || super.onSupportNavigateUp()
    }


}