package com.example.roomyweather.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomyweather.R
import com.example.roomyweather.data.FiveDayForecastEntity
import com.example.roomyweather.data.ForecastPeriod
import com.google.android.material.progressindicator.CircularProgressIndicator


class Forecast_search_fragment : Fragment(R.layout.forecast_search_fragment) {
    private val tag = "MainActivity"

    private val viewModel: FiveDayForecastViewModel by viewModels()
    private val forecastAdapter = ForecastAdapter(::onForecastItemClick)

    private lateinit var forecastListRV: RecyclerView
    private lateinit var loadingErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    private var fiveDayForecastEntity : FiveDayForecastEntity? = null
    private val viewModel2: SavedDataRepoViewModel by viewModels()
    private var cities : List<String>? = null

    override fun onViewCreated(
        view : View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        loadingErrorTV = view.findViewById(R.id.tv_loading_error)
        loadingIndicator = view.findViewById(R.id.loading_indicator)

        /*
         * Set up RecyclerView.
         */
        forecastListRV = view.findViewById(R.id.rv_forecast_list)
        forecastListRV.layoutManager = LinearLayoutManager(requireContext())
        forecastListRV.setHasFixedSize(true)
        forecastListRV.adapter = forecastAdapter

        /*
         * Set up an observer on the current forecast data.  If the forecast is not null, display
         * it in the UI.
         */
        viewModel.forecast.observe(viewLifecycleOwner) { forecast ->
            if (forecast != null) {
                forecastAdapter.updateForecast(forecast)
                forecastListRV.visibility = View.VISIBLE
                forecastListRV.scrollToPosition(0)
                (requireActivity() as AppCompatActivity).supportActionBar?.title = forecast.city.name
            }
        }

        /*
         * Set up an observer on the error associated with the current API call.  If the error is
         * not null, display the error that occurred in the UI.
         */
        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                loadingErrorTV.text = getString(R.string.loading_error, error.message)
                loadingErrorTV.visibility = View.VISIBLE
                Log.e(tag, "Error fetching forecast: ${error.message}")
            }
        }

        /*
         * Set up an observer on the loading status of the API query.  Display the correct UI
         * elements based on the current loading status.
         */
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                loadingIndicator.visibility = View.VISIBLE
                loadingErrorTV.visibility = View.INVISIBLE
                forecastListRV.visibility = View.INVISIBLE
            } else {
                loadingIndicator.visibility = View.INVISIBLE
            }
        }
    }

    fun onClickSubMenu(city : String){
        val city = city
        onResume()
    }

    override fun onResume() {
        super.onResume()

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val city = sharedPrefs.getString(getString(R.string.pref_city_key), "Corvallis,OR,US")
        Log.d("forecast",city!!)
        val units = sharedPrefs.getString(getString(R.string.pref_units_key), null)
        viewModel.loadFiveDayForecast(city, units, "1d6fc0b7fc7386e98176eabc4a18e953")

        // ADD CITY IN THE DATABASE
        fiveDayForecastEntity = FiveDayForecastEntity(city!!,System.currentTimeMillis()!!)
        viewModel2.addBookmarkedRepo(fiveDayForecastEntity!!)

    }

    /**
     * This method is passed into the RecyclerView adapter to handle clicks on individual items
     * in the list of forecast items.  When a forecast item is clicked, a new activity is launched
     * to view its details.
     */
    private fun onForecastItemClick(forecastPeriod: ForecastPeriod) {
        Log.d(tag, "onForecastItemClick called , forecast : $forecastPeriod" )
        val directions = Forecast_search_fragmentDirections.navigateToForecastDetail(forecastAdapter.forecastCity!!,forecastPeriod)
        findNavController().navigate(directions)
    }

}