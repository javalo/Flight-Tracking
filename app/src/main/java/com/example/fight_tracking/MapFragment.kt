package com.example.fight_tracking

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.fight_tracking.R.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_flight_detail.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlin.math.log


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap : GoogleMap
    private var mapReady = false
    private lateinit var viewModel: FlightListViewModel


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var rootView =  inflater.inflate(layout.fragment_map, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(FlightListViewModel::class.java)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap -> mMap = googleMap
            mapReady = true
            //updateMap()
            viewModel.getSelectedFlightInfoiveData().observe(this, {
                updateMap(it)
                Log.e(TAG, "HAHAHAHAHAHAHAHAHHAHAHAHAAHHAHAHAAAHAHAHA ", )
            })
        }

       /* viewModel.getSelectedFlightNameLiveData().observe(this, {
            updateMap(it)
            Log.e(TAG, "HAHAHAHAHAHAHAHAHHAHAHAHAAHHAHAHAAAHAHAHA ", )
        })
*/
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
      /* activity.let {
            viewModel = ViewModelProvider(requireActivity()).get(FlightListViewModel::class.java)
        }

*/
        viewModel.getSelectedFlightInfoiveData().observe(this, {
            //flight_name.text = it

            updateMap(it)
        })
    }


    private fun updateMap(cordonnee:List<String>) {

       if (mapReady) {

           val marker1 = LatLng(cordonnee.get(0).toDouble(), cordonnee.get(1).toDouble())


          val marke2 = LatLng(cordonnee.get(2).toDouble(), cordonnee.get(3).toDouble())


           mMap.addMarker(MarkerOptions().position(marke2).title(cordonnee.get(4)).icon(
                   Utils.bitmapDescriptorFromVector(
                           activity,
                           R.drawable.ic_flight
                   )
           ))


           mMap.addMarker(MarkerOptions().position(marker1).title(cordonnee.get(4)).icon(
                   Utils.bitmapDescriptorFromVector(
                           activity,
                           R.drawable.ic_flight
                   )
           ))

         }


    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let { mMap = it}
    }
}