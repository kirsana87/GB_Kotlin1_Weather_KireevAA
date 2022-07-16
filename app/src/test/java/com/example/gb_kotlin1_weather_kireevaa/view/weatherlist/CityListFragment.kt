package com.example.gb_kotlin1_weather_kireevaa.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import com.example.gb_kotlin1_weather_kireevaa.repository.Weather
import com.google.android.material.snackbar.Snackbar
import com.example.gb_kotlin1_weather_kireevaa.R
import com.example.gb_kotlin1_weather_kireevaa.details.DetailsFragment
import com.example.gb_kotlin1_weather_kireevaa.details.KEY_BUNDLE_WEATHER
import com.example.gb_kotlin1_weather_kireevaa.viewmodel.MainFragment



class CityListFragment : Fragment(), OnItemClickListener {
    private var _banding: FragmentListBinding? = null
    private val binding get() = _banding!!
    private val viewModel: CityViewModel by lazy {
        ViewModelProvider(this).get(CityViewModel::class.java)
    }
    private val adapter = WeatherListAdapter(this)
    private var isRussian = true
    private var weatherList: List<Weather> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _banding = FragmentListBinding.inflate(inflater, container, false)

        initRecycler()
        return binding.root
    }

    private fun initRecycler() {
        binding.listWeatherRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<MainFragment.AppState> { data -> render(data) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)

        binding.floatingActionButton.setOnClickListener {
            isRussian = !isRussian
            if (isRussian) {
                viewModel.getDataWeatherRussia()
                binding.floatingActionButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_russia
                    )
                )
            } else {
                viewModel.getDataWeatherWorld()
                binding.floatingActionButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_earth
                    )
                )
            }
        }
        viewModel.getDataWeatherRussia()
    }

    private fun render(data: AppState) {
        when (data) {
            is MainFragment.AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.loadingLayout.showSnackBar(data.error.toString())
//                Snackbar.make(
//                    binding.loadingLayout,
//                    data.error.toString(),
//                    Snackbar.LENGTH_INDEFINITE
//                ).setAction("Попробывать еще раз") {
//                    viewModel.getDataWeatherRussia()
//                }.show()
            }
            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                if (weatherList.isEmpty()) {
                    weatherList = data.weatherList
                    adapter.setData(data.weatherList)
                } else {
                    val weatherListDiffUtilCallback =
                        WeatherListDiffUtilCallback(weatherList, data.weatherList)

                    val productDiffResult = DiffUtil.calculateDiff(weatherListDiffUtilCallback)
                    adapter.setData(data.weatherList)
                    productDiffResult.dispatchUpdatesTo(adapter)
                }
                weatherList = data.weatherList
            }
        }
    }

    companion object {
        fun newInstance() = CityListFragment()
    }

    override fun onDestroy() {
        _banding = null
        super.onDestroy()
    }

    override fun onItemClick(weather: Weather) {
        val bundle = Bundle()
        bundle.putParcelable(KEY_BUNDLE_WEATHER, weather)
        requireActivity().supportFragmentManager.beginTransaction()

            .add(
                R.id.container,
                DetailsFragment.newInstance(bundle)
            ).addToBackStack("").commit()
    }
}