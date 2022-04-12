package com.example.weatherapp.ui.search

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.CurrentConditions
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search),
    ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>

    @Inject
    lateinit var viewModel: SearchViewModel

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        binding.submitButton.setOnClickListener {
            viewModel.submitButtonClicked()
        }
        // Set Title
        activity?.title = "Search"

        viewModel.enableButton.observe(viewLifecycleOwner) { enable ->
            binding.submitButton.isEnabled = enable
        }

        viewModel.showErrorDialog.observe(viewLifecycleOwner) { showError ->
            if (showError) {
                ErrorDialogFragment().show(childFragmentManager, ErrorDialogFragment.TAG)
            }
        }

        viewModel.currentConditions.observe(viewLifecycleOwner) { currentConditions ->
            navigateToCurrentConditions(currentConditions)
        }

        binding.zipCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0.toString().let { viewModel.updateZipCode(it) }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        //Begin Location Permission Portion

        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    requestLocationUpdates()

                }
                else -> {
                    // No location access granted.
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()

        binding.locationButton.setOnClickListener {
            requestLocation()
            requestLocationUpdates()

        }
    }


    private fun requestLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            AlertDialog.Builder(requireActivity())
                .setMessage("Permission required to access location!")
                .setNeutralButton("Ok") { _, _ ->
                    locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
                }
                .show()
        } else {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

    }

    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this.requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        val locationRequest = LocationRequest.create()
        locationRequest.interval = 0L
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val locationProvider = LocationServices.getFusedLocationProviderClient(requireActivity())
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
            }
        }
        locationProvider.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        locationProvider.lastLocation.addOnSuccessListener {
            if(it != null){
                Log.d("SearchFragment", it.toString())
                viewModel.updateCoordinates(it.latitude, it.longitude)
                viewModel.locationButtonClicked()
            }
            else{
                Log.d("SearchFragment", "Location is null")
            }
        }

    }

    private fun navigateToCurrentConditions(currentConditions: CurrentConditions) {
        val zipCode = viewModel.getZipCode()
        val action = SearchFragmentDirections.actionSearchFragmentToCurrentConditionsFragment(
            zipCode,
            currentConditions,
            viewModel.getLat(),
            viewModel.getLon()
        )
        findNavController().navigate(action)
    }

}