package com.example.weatherapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MyAdapter(private val data: List<DayForecast>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    @SuppressLint("NewApi")
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //Date formatting
        private val formatter = DateTimeFormatter.ofPattern("MMM d")
        private val timeFormatter = DateTimeFormatter.ofPattern("h:mma")
        private val dateView: TextView = view.findViewById(R.id.date)
        private val sunriseView: TextView = view.findViewById(R.id.sunrise)
        private val sunsetView: TextView = view.findViewById(R.id.sunset)
        private val dayTempView: TextView = view.findViewById(R.id.temp)
        private val highTempView: TextView = view.findViewById(R.id.highTemp)
        private val lowTempView: TextView = view.findViewById(R.id.lowTemp)
        private val iconView: ImageView = view.findViewById(R.id.forecast_icon)

        fun bind(data: DayForecast) {
            val instant = Instant.ofEpochSecond(data.date)
            val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            dateView.text = formatter.format(dateTime)

            val sunriseTime =
                LocalDateTime.ofInstant(Instant.ofEpochSecond(data.sunrise), ZoneId.systemDefault())
            sunriseView.text = timeFormatter.format(sunriseTime)

            val sunsetTime =
                LocalDateTime.ofInstant(Instant.ofEpochSecond(data.sunset), ZoneId.systemDefault())
            sunsetView.text = timeFormatter.format(sunsetTime)

            dayTempView.text = dayTempView.context.getString(R.string.temp, data.temp.day.toInt())

            highTempView.text = highTempView.context.getString(R.string.highTemp, data.temp.max.toInt())

            lowTempView.text = lowTempView.context.getString(R.string.lowTemp, data.temp.min.toInt())

            val iconName = data.weather.firstOrNull()?.icon
            val iconUrl = "https://openweathermap.org/img/wn/${iconName}@2x.png"
            Glide.with(iconView)
                .load(iconUrl)
                .placeholder(R.drawable.sun)
                .into(iconView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_date, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

    }

    override fun getItemCount() = data.size
}