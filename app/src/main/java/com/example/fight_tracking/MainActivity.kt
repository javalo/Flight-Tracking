package com.example.fight_tracking

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.content.Intent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    val DATE_FORMAT = "dd MMM yyy"

    val fromCalendar = Calendar.getInstance()
    val toCalendar = Calendar.getInstance()

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val airportNamesList = ArrayList<String>()


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        for (airport in viewModel.getAirportListLiveData().value!!) {
            airportNamesList.add(airport.getFormattedName())
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            R.layout.style_spinner,
            airportNamesList
        )

        spinner_airport.adapter = adapter

        viewModel.getBeginDateLiveData()
            .observe(this, { displaySelectedDate(fromDate, it) })

        viewModel.getEndDateLiveData()
            .observe(this, { displaySelectedDate(toDate, it) })

        fromDate.setOnClickListener { showDatePicker(fromDate) }
        toDate.setOnClickListener { showDatePicker(toDate) }

        searchButton.setOnClickListener { search() }
    }

    private fun showDatePicker(clickedView: View) {
        val calendar = if (clickedView.id == fromDate.id) {
            viewModel.getBeginDateLiveData().value
        } else {
            viewModel.getEndDateLiveData().value
        }
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (clickedView.id == fromDate.id){
                    viewModel.updateBeginCalendar(year, monthOfYear, dayOfMonth)
                }else{
                    viewModel.updateEndCalendar(year, monthOfYear, dayOfMonth)
                }
            },
            calendar!!.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dpd.show()
    }

    private fun displaySelectedDate(textView: TextView, calendar: Calendar) {
        textView.text = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(calendar.time)
    }


    private fun search(){
        // get the airport
        val icao = viewModel.getAirportListLiveData().value!![spinner_airport.selectedItemPosition].icao
        // check if isArrival true then change the value from false to true
        val isArrival = switch_type.isChecked

        // get the dates
        //val begin = fromCalendar.timeInMillis / 1000
        //val end = toCalendar.timeInMillis / 1000

        //Log.d("MainActivity", "icao = $icao, isArrival = $isArrival, begin = $begin, end = $end")
        // next call an other Activity


        val i = Intent(this, FlightListActivity::class.java)
        i.putExtra("begin", viewModel.getBeginDateLiveData().value!!.timeInMillis / 1000)
        i.putExtra("end", viewModel.getEndDateLiveData().value!!.timeInMillis / 1000)
        i.putExtra("icao", viewModel?.getAirportListLiveData().value?.get(spinner_airport.selectedItemPosition)?.icao)
        i.putExtra("isArrival", switch_type.isChecked)
        startActivity(i)
    }

}