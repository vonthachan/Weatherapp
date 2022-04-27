package com.example.weatherapp.ui.currentconditions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCurrentConditionsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CurrentConditionsFragment : Fragment() {

    private lateinit var binding: FragmentCurrentConditionsBinding

    @Inject
    lateinit var viewModel: CurrentConditionsViewModel
    private val args: CurrentConditionsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_current_conditions, container, false)
        binding = FragmentCurrentConditionsBinding.bind(view)
        binding.button.setOnClickListener {
            navigateToForecast()
        }
        activity?.title = "Current Conditions"
        return view
    }

    override fun onResume() {
        super.onResume()
        viewModel.currentConditions.observe(this) {
            bindData()
        }
        viewModel.loadData()
    }

    private fun navigateToForecast() {
        val action =
            CurrentConditionsFragmentDirections.actionCurrentConditionsFragmentToForecastFragment(
                args.zipCode,
                args.lat,
                args.lon
            )
        findNavController().navigate(action)
    }

    private fun bindData() {

        binding.cityName.text = args.currentConditions.name
        binding.currentTemperature.text =
            getString(R.string.currentTemperature, args.currentConditions.main.temp.toInt())
        binding.feelsLike.text =
            getString(R.string.feels_like, args.currentConditions.main.feelsLike.toInt())
        binding.low.text = getString(R.string.low, args.currentConditions.main.tempMin.toInt())
        binding.high.text = getString(R.string.high, args.currentConditions.main.tempMax.toInt())
        binding.humidity.text =
            getString(R.string.humidity, args.currentConditions.main.humidity.toInt())
        binding.pressure.text =
            getString(R.string.pressure, args.currentConditions.main.pressure.toInt())
        val iconName = args.currentConditions.weather.firstOrNull()?.icon
        Glide.with(this)
            .load("https://openweathermap.org/img/wn/${iconName}@2x.png")
            .into(binding.conditionIcon)
    }
}