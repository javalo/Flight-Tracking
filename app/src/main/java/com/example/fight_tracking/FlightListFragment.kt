package com.example.fight_tracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_flight_list.*
import kotlinx.android.synthetic.main.fragment_flight_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FlightListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FlightListFragment : Fragment() ,  FlightListRecyclerAdapter.OnItemClickListener{

    lateinit var viewModel: FlightListViewModel
    lateinit var viewModelmain: MainViewModel

    val CordoneeAirports = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(requireActivity()).get(FlightListViewModel::class.java)
        viewModelmain = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.flightListLiveData.observe(this, Observer {
            if (it == null || it.isEmpty()) {
                //DISPLAY ERROR
            } else {
                // SetUp my recycleView
                val adapter = FlightListRecyclerAdapter()
                adapter.flightList = it
                adapter.onItemClickListener = this
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

            }
        })

        viewModel.isLoadingLiveData.observe(this, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE

            } else {
                progressBar.visibility = View.INVISIBLE
            }
        })

        return inflater.inflate(R.layout.fragment_flight_list, container, false)
    }


    override fun onItemClicked(flightName: String, depart: String, arrive: String) {
        //DO SOMETHING WHEN CLICKING ON THE FLIGHT NAME

        if(arrive==null)
        Log.e("Mother Fucker is null  ", "null crazy")
        else
            Log.e("Mother Fucker NOT null" , arrive)

        for (airport in viewModelmain.getAirportListLiveData().value!!) {

          if(airport.icao == depart){
              CordoneeAirports.add(airport.lat)
              CordoneeAirports.add(airport.lon)
            }

            if(airport.icao == arrive){
                CordoneeAirports.add(airport.lat)
                CordoneeAirports.add(airport.lon)
            }

        }

        CordoneeAirports.add(flightName)


        viewModel.updateSelectedFlightInfo(CordoneeAirports)
    }
}