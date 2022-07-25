package com.example.gb_kotlin1_weather_kireevaa.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gb_kotlin1_weather_kireevaa.R
import com.example.gb_kotlin1_weather_kireevaa.databinding.FragmentWeatherDetailBinding
import com.example.gb_kotlin1_weather_kireevaa.model.City
import com.example.gb_kotlin1_weather_kireevaa.model.Weather
import com.example.gb_kotlin1_weather_kireevaa.viewmodel.AppState
import com.example.gb_kotlin1_weather_kireevaa.viewmodel.WeatherDTOModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather_detail.*


class WeatherFragmentDetail : Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): WeatherFragmentDetail {
            return WeatherFragmentDetail().apply { arguments = bundle }
        }
    }

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!
    private var city: City? = Weather().city // default city

    private val viewModel: WeatherDTOModel by lazy {
        ViewModelProvider(this)[WeatherDTOModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let { weather ->
            city = weather.city
        }

        viewModel.getLiveDataDTO().observe(viewLifecycleOwner) { appState -> renderData(appState) }
        city?.let { viewModel.getWeather(it) }
    }

    private fun renderData(appState: AppState) = when (appState) {
        is AppState.SuccessFromServer -> {
            with(binding) {
                cityName.text = city.name
                appState.weatherDTO.fact.run {
                    temperatureValue.text = temp.toString()
                    feelsLikeValue.text = feelsLike.toString()
                }
            }
        }
        is AppState.Error -> {
            Snackbar
                .make(binding.root, "Error", Snackbar.LENGTH_INDEFINITE)
                .setAction("Reload") { viewModel.getWeather(city) }
                .show()
        }
        else -> {}
    }.also {
        if (appState == AppState.Loading) binding.loadingLayout.visibility = View.VISIBLE
        else binding.loadingLayout.visibility = View.GONE
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}