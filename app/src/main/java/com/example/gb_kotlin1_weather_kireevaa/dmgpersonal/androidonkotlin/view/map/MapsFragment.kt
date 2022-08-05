package com.example.gb_kotlin1_weather_kireevaa.view.map

import android.Manifest
import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import ccom.example.gb_kotlin1_weather_kireevaa.utils.MAP_ZOOM_VALUE
import com.dmgpersonal.androidonkotlin.MyApp
import com.example.gb_kotlin1_weather_kireevaa.MyApp
import com.example.gb_kotlin1_weather_kireevaa.R
import com.example.gb_kotlin1_weather_kireevaa.model.getAddress
import com.example.gb_kotlin1_weather_kireevaa.model.getCoordinates
import com.example.gb_kotlin1_weather_kireevaa.model.getDefaultCity
import com.example.gb_kotlin1_weather_kireevaa.utils.checkPermission
import com.example.gb_kotlin1_weather_kireevaa.utils.hideKeyboard
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private var currentLocation = getDefaultCity()
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        val currentPosition = LatLng(currentLocation.lat, currentLocation.lon)
        googleMap.addMarker(
            MarkerOptions()
                .position(currentPosition).title("Marker in ${currentLocation.name}")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, MAP_ZOOM_VALUE))
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.setOnMapClickListener {
            hideKeyboard(requireView())
        }
        map = googleMap
    }

    companion object {
        fun newInstance() = MapsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        getCurrentLocation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.addressButton.setOnClickListener { setMarker() }
    }

    private fun setMarker() {
        if (!binding.addressEditText.text.isNullOrEmpty()) {
            currentLocation = getCoordinates(binding.addressEditText.text.toString())
            hideKeyboard(requireView())
            if (currentLocation.name.contains("Default city")) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Ошибка")
                    .setMessage("Локация ${binding.addressEditText.text.toString()} не найдена")
                    .setNeutralButton("OK") { _, _ -> hideKeyboard(requireView()) }
                    .create()
                    .show()
                binding.addressEditText.text!!.clear()
            } else if (checkPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    getString(R.string.location_alert_title),
                    getString(R.string.location_alert_request_text)))
            {
                val currentPosition = LatLng(currentLocation.lat, currentLocation.lon)
                map.addMarker(
                    MarkerOptions()
                        .position(currentPosition)
                        .title(currentLocation.name)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)))
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, MAP_ZOOM_VALUE))
            }
        }
    }

    /*************************************** Location ***************************************/
    private fun getCurrentLocation() {
        val locationManager =
            MyApp.appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                currentLocation = getAddress(location.latitude, location.longitude)
            }
        }

        if (hasNetwork || hasGps && checkPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION,
                getString(R.string.location_alert_title),
                getString(R.string.location_alert_request_text)
            )
        ) {
            if (hasGps && checkPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    getString(R.string.location_alert_title),
                    getString(R.string.location_alert_request_text)
                )
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 500L,
                    0F, locationListener
                )
            } else if (checkPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    getString(R.string.location_alert_title),
                    getString(R.string.location_alert_request_text)
                )
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 500L,
                    0F, locationListener
                )
            }
        }
    }
}