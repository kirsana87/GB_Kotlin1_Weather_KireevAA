package com.example.gb_kotlin1_weather_kireevaa.view.cities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dmgpersonal.androidonkotlin.R
import com.dmgpersonal.androidonkotlin.model.Weather
import com.example.gb_kotlin1_weather_kireevaa.R
import com.example.gb_kotlin1_weather_kireevaa.cities.CitiesListDiffUtilCallback
import com.example.gb_kotlin1_weather_kireevaa.model.Weather

class CitiesFragmentAdapter(
    private var onItemViewClickListener: CitiesListFragment.OnItemViewClickListener?
) :
    RecyclerView.Adapter<CitiesFragmentAdapter.CitiesViewHolder>() {

    private var weatherData: List<Weather> = listOf()

    //@SuppressLint("NotifyDataSetChanged")
    fun setWeather(data: List<Weather>) {

        val cityDiffUtil = CitiesListDiffUtilCallback(weatherData, data)
        val result = DiffUtil.calculateDiff(cityDiffUtil)

        weatherData = data
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : CitiesViewHolder {
        return CitiesViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_cities_list_recycler_item, parent, false)
        as View) }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int { return weatherData.size }

    fun removeListener() {
        onItemViewClickListener = null
    }

    inner class CitiesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) {
            itemView.apply {
                findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text = weather.city.name
                setOnClickListener { onItemViewClickListener?.onItemViewClick(weather) }
            }
        }
    }
}