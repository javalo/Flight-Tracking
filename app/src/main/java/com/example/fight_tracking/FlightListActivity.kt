package com.example.fight_tracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_flight_list.*


class FlightListActivity : AppCompatActivity()  {


     lateinit var viewModel: FlightListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_list)

        val isMobile = mapFragment == null

        viewModel = ViewModelProvider(this).get(FlightListViewModel::class.java)
        viewModel.search(
                intent.getStringExtra("icao")!!,
                intent.getBooleanExtra("isArrival", false),
                intent.getLongExtra("begin", 0),
                intent.getLongExtra("end", 0)
        )

        viewModel.getSelectedFlightInfoiveData().observe(this, {
            //switch fragment
            val newFragment: FlightDetailFragment = FlightDetailFragment.newInstance()
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

            if (isMobile)
                transaction.add(R.id.activityContainer, newFragment)
            else
                transaction.add(R.id.mapFragment, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
    })

    }



}