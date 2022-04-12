package com.example.weatherapp.ui.forecast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentForecastBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForecastFragment : Fragment(R.layout.fragment_forecast) {
    private lateinit var binding: FragmentForecastBinding
    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var viewModel: ForecastViewModel
    private val args: ForecastFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForecastBinding.bind(view)
        activity?.title = "Forecast"
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onResume() {
        super.onResume()
        viewModel.forecast.observe(this) { forecast ->
            binding.recyclerView.adapter = MyAdapter(forecast.list)
        }
        if (args.zipCode != null){
            viewModel.loadData(args.zipCode!!)
        } else {
            viewModel.loadData(args.lat!!.toDouble(), args.lon!!.toDouble())
        }
    }
}
