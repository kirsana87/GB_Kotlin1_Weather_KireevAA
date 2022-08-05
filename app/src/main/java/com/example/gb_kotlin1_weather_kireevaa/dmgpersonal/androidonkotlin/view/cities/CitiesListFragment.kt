package com.example.gb_kotlin1_weather_kireevaa.view.cities

import android.Manifest
import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ccom.example.gb_kotlin1_weather_kireevaa.utils.SP_KEY_LOCATION
import ccom.example.gb_kotlin1_weather_kireevaa.utils.SP_REGION_SETTINGS
import com.example.gb_kotlin1_weather_kireevaa.MyApp
import com.example.gb_kotlin1_weather_kireevaa.R
import com.example.gb_kotlin1_weather_kireevaa.model.Weather
import com.example.gb_kotlin1_weather_kireevaa.model.getAddress
import com.example.gb_kotlin1_weather_kireevaa.model.getDefaultCity
import com.example.gb_kotlin1_weather_kireevaa.utils.checkPermission
import com.example.gb_kotlin1_weather_kireevaa.view.details.WeatherFragmentDetails
import com.example.gb_kotlin1_weather_kireevaa.viewmodel.AppStateLocal
import com.example.gb_kotlin1_weather_kireevaa.viewmodel.WeatherViewModelList
import com.google.android.material.snackbar.Snackbar

class CitiesListFragment : Fragment() {

    private var _binding: FragmentCitiesListBinding? = null
    private val binding get() = _binding!!
    private var sharedPreferences: SharedPreferences? = null
    private var location = com.example.gb_kotlin1_weather_kireevaa.model.Location.Russia
    private var currentLocation = getDefaultCity()

    private val viewModel: WeatherViewModelList by lazy {
        ViewModelProvider(this)[WeatherViewModelList::class.java]
    }

    private val adapter = CitiesFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .add(
                        R.id.container, WeatherFragmentDetails.newInstance(
                        Bundle().apply {
                            putParcelable(WeatherFragmentDetails.BUNDLE_EXTRA, weather)
                        }
                    ))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
    })

    companion object {
        fun newInstance() = CitiesListFragment()
    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences = context?.getSharedPreferences(SP_REGION_SETTINGS, Context.MODE_PRIVATE)
        location = when(sharedPreferences?.getBoolean(SP_KEY_LOCATION, true)) {
            true -> com.example.gb_kotlin1_weather_kireevaa.model.Location.Russia
            false -> com.example.gb_kotlin1_weather_kireevaa.model.Location.World
            else -> com.example.gb_kotlin1_weather_kireevaa.model.Location.Russia
        }

        _binding = FragmentCitiesListBinding.inflate(inflater, container, false)

        when (location) {
            com.example.gb_kotlin1_weather_kireevaa.model.Location.Russia -> binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            com.example.gb_kotlin1_weather_kireevaa.model.Location.World -> binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener { changeDataSet() }

        viewModel.getLiveData().observe(viewLifecycleOwner) { appState -> renderData(appState) }
        viewModel.getWeather(location)

        binding.mapFragmentFAB.setOnClickListener {
            showCurrentLocationWeather()
        }
        getCurrentLocation()
    }

    private fun showCurrentLocationWeather() {
        getCurrentLocation()
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, WeatherFragmentDetails.newInstance(Bundle().apply {
                putParcelable(WeatherFragmentDetails.BUNDLE_EXTRA, Weather(currentLocation))
            }))
            .addToBackStack("")
            .commit()
        }

    private fun renderData(appState: AppStateLocal) = when (appState) {
        is AppStateLocal.Success -> {
            adapter.setWeather(appState.weatherData)
        }
        is AppStateLocal.Error -> {
            with(binding) {
                citiesFragmentRootLayout.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getWeather(location) })
            }
        }
        else -> {}
    }.also {
        if (appState == AppStateLocal.Loading) binding.citiesFragmentLoadingLayout.visibility =
            View.VISIBLE
        else binding.citiesFragmentLoadingLayout.visibility = View.GONE
    }

    private fun changeDataSet() {
        location = !location
        viewModel.getWeather(location)
        when (location) {
            com.example.gb_kotlin1_weather_kireevaa.model.Location.Russia -> binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            com.example.gb_kotlin1_weather_kireevaa.model.Location.World -> binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
        }

        sharedPreferences?.apply {
            edit()
                .putBoolean(SP_KEY_LOCATION, when(location) {
                    com.example.gb_kotlin1_weather_kireevaa.model.Location.Russia -> true
                    com.example.gb_kotlin1_weather_kireevaa.model.Location.World -> false })
                .apply()
        }
    }


    override fun onDestroy() {
        _binding = null
        adapter.removeListener()
        super.onDestroy()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }


    private fun getCurrentLocation() {
        val locationManager =
            MyApp.appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                currentLocation = getAddress(location.latitude, location.longitude)
            }
        }

        if (hasNetwork || hasGps && checkPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION,
                getString(R.string.location_alert_title),
                getString(R.string.location_alert_request_text)
            )) {
            if (hasGps && checkPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    getString(R.string.location_alert_title),
                    getString(R.string.location_alert_request_text)
            )) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 500L,
                    0F, locationListener)
            } else if (checkPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    getString(R.string.location_alert_title),
                    getString(R.string.location_alert_request_text)
            )){
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 500L,
                    0F, locationListener)
            }
        }
    }
}