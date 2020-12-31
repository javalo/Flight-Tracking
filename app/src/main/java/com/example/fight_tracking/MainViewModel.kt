package com.example.fight_tracking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel(){
    private val airportListLiveData : MutableLiveData<List<Airport>> = MutableLiveData()


    init {
        airportListLiveData.value = Utils.generateAirportList()
    }


    fun getAirportListLiveData(): LiveData<List<Airport>>{
        return airportListLiveData
    }

}