package com.example.gb_kotlin1_weather_kireevaa.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dmgpersonal.androidonkotlin.R
import com.dmgpersonal.androidonkotlin.model.Weather
import com.example.gb_kotlin1_weather_kireevaa.R
import com.example.gb_kotlin1_weather_kireevaa.model.Weather

class HistoryFragmentAdapter(
    private var onItemViewClickListener: HistoryListFragment.OnItemViewClickListener?
) :
    RecyclerView.Adapter<HistoryFragmentAdapter.HistoryViewHolder>() {

    private var weatherData: List<Weather> = listOf()

    fun setWeather(data: List<Weather>) {

        val historyDiffUtil = HistoryListDiffUtilCallback(weatherData, data)
        val result = DiffUtil.calculateDiff(historyDiffUtil)

        weatherData = data
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : HistoryViewHolder {
        return HistoryViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_history_list_recycler_item, parent, false)
        as View) }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int { return weatherData.size }

    fun removeListener() {
        onItemViewClickListener = null
    }

    inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) {
            itemView.apply {
                findViewById<AppCompatTextView>(R.id.historyCityRecycleViewItem).text = weather.city.name
                findViewById<AppCompatTextView>(R.id.historyTempRecycleViewItem).text = weather.temperature.toString()
                setOnClickListener { onItemViewClickListener?.onItemViewClick(weather) }
            }
        }
    }
}