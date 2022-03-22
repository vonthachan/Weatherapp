package com.example.weatherapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.RowDateBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MyAdapter(private val data: List<DayForecast>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    @SuppressLint("NewApi")
    class ViewHolder(private val binding: RowDateBinding) : RecyclerView.ViewHolder(binding.root) {

        //Date formatting
        private val formatter = DateTimeFormatter.ofPattern("MMM d")
        private val timeFormatter = DateTimeFormatter.ofPattern("h:mma")

        fun bind(data: DayForecast) {
            val instant = Instant.ofEpochSecond(data.date)
            val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            binding.date.text = formatter.format(dateTime)

            val sunriseTime =
                LocalDateTime.ofInstant(Instant.ofEpochSecond(data.sunrise), ZoneId.systemDefault())
            binding.sunrise.text = timeFormatter.format(sunriseTime)

            val sunsetTime =
                LocalDateTime.ofInstant(Instant.ofEpochSecond(data.sunset), ZoneId.systemDefault())
            binding.sunset.text = timeFormatter.format(sunsetTime)

            binding.temp.text = binding.temp.context.getString(R.string.temp, data.temp.day.toInt())

            binding.highTemp.text =
                binding.highTemp.context.getString(R.string.highTemp, data.temp.max.toInt())

            binding.lowTemp.text =
                binding.lowTemp.context.getString(R.string.lowTemp, data.temp.min.toInt())

            val iconName = data.weather.firstOrNull()?.icon
            Glide.with(binding.forecastIcon)
                .load("https://openweathermap.org/img/wn/${iconName}@2x.png")
                .placeholder(R.drawable.sun)
                .into(binding.forecastIcon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}