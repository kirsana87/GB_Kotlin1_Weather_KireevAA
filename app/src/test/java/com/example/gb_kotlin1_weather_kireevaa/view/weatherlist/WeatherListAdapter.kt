package com.example.gb_kotlin1_weather_kireevaa.view.weatherlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gb_kotlin1_weather_kireevaa.repository.Weather


class WeatherListAdapter(
    private val onItemClickListener: OnItemClickListener,
    private var data: List<Weather> = listOf()
) :
    RecyclerView.Adapter<WeatherListAdapter.CityHolder>() {


    fun setData(newData: List<Weather>) {
        this.data = newData
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherListAdapter.CityHolder {
        val binding =
            ItemRecyclerWeahterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityHolder(binding.root)
    }

    override fun onBindViewHolder(holder: WeatherListAdapter.CityHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weather: Weather) {
            val binding = ItemRecyclerWeahterBinding.bind(itemView)
            binding.cityName.text = weather.city.name
            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(weather)
            }

        }
    }
}