package com.example.gb_kotlin1_weather_kireevaa.view.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gb_kotlin1_weather_kireevaa.R
import com.example.gb_kotlin1_weather_kireevaa.databinding.ActivityMainBinding.inflate
import com.example.gb_kotlin1_weather_kireevaa.databinding.FragmentCitiesListBinding
import com.example.gb_kotlin1_weather_kireevaa.model.Location
import com.example.gb_kotlin1_weather_kireevaa.model.Weather
import com.example.gb_kotlin1_weather_kireevaa.view.cities.cities.CitiesFragmentAdapter
import com.example.gb_kotlin1_weather_kireevaa.view.details.WeatherFragmentDetail
import com.example.gb_kotlin1_weather_kireevaa.viewmodel.AppState
import com.example.gb_kotlin1_weather_kireevaa.viewmodel.WeatherViewModelList
import com.google.android.material.snackbar.Snackbar

class CitiesListFragment : Fragment() {

    private var _binding: FragmentCitiesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherViewModelList by lazy {
        ViewModelProvider(this)[WeatherViewModelList::class.java]
    }

    private val adapter = CitiesFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            activity?.run {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, WeatherFragmentDetail.newInstance(
                        Bundle().apply {
                            putParcelable(WeatherFragmentDetail.BUNDLE_EXTRA, weather)}
                    ))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    private var location = Location.Russia

    companion object {
        fun newInstance() = CitiesListFragment()
    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) { Snackbar.make(this, text, length).setAction(actionText, action).show() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener { changeDataSet() }

        viewModel.getLiveData().observe(viewLifecycleOwner) { appState -> renderData(appState) }
        viewModel.getWeather(location)
    }

    private fun renderData(appState: AppState) = when (appState) {
        is AppState.Success -> {
            adapter.setWeather(appState.weatherData)
        }
        is AppState.Error -> {
            with(binding) {
                citiesFragmentRootLayout.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getWeather(location) })
            }
        }
        else -> {}
    }.also {
        if (appState == AppState.Loading) binding.citiesFragmentLoadingLayout.visibility = View.VISIBLE
        else binding.citiesFragmentLoadingLayout.visibility = View.GONE
    }

    private fun changeDataSet() {
        location = !location
        viewModel.getWeather(location)
        when (location) {
            Location.Russia -> binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            Location.World -> binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
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
}
