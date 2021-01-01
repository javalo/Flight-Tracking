package com.example.fight_tracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

class FlightListActivity : AppCompatActivity() {

    lateinit var viewModel: FlightListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_list)


        viewModel = ViewModelProvider(this).get(FlightListViewModel::class.java)
        viewModel.search(
                intent.getStringExtra("icao")!!,
                intent.getBooleanExtra("isArrival", false),
                intent.getLongExtra("begin", 0),
                intent.getLongExtra("end", 0)
        )

    }
}