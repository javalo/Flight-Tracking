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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var viewModel: FlightListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(requireActivity()).get(FlightListViewModel::class.java)

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



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FlightListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FlightListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onItemClicked(flightName: String) {
        //DO SOMETHING WHEN CLICKING ON THE FLIGHT NAME

        Log.d("ViewClicked", flightName)

        viewModel.updateSelectedFlightName(flightName)
    }
}