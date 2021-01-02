package com.example.fight_tracking
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

class FlightInfoCell : LinearLayout {


    var flightName: TextView? = null
        private set
    var depAirportName: TextView? = null
        private set
    var arrAirportName: TextView? = null
        private set
    var flightTime: TextView? = null
        private set
    var depDate: TextView? = null
        private set
    var arrDate: TextView? = null
        private set
    var depHour: TextView? = null
        private set
    var arrHour: TextView? = null
        private set

    var lineareLiyoutPrincipal: RelativeLayout? = null
        private set



    constructor(context: Context?) : super(context) {
        initLayout()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initLayout()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initLayout()
    }

    private fun bindViews() {
        flightName = findViewById(R.id.flight_name)
        depAirportName = findViewById(R.id.departure_airport)
        arrAirportName = findViewById(R.id.arrival_airport)
        flightTime = findViewById(R.id.flight_duration)
        depDate = findViewById(R.id.departure_date)
        arrDate = findViewById(R.id.arrival_date)
        depHour = findViewById(R.id.departure_hour)
        arrHour = findViewById(R.id.arrival_hour)
        lineareLiyoutPrincipal = findViewById(R.id.container)
    }


    // CACA not a good architecture to be add here (the view ) shoud be in Adabter       but the reusebility we add here
    fun bindData(flight : FlightModel,  position: Int ){
        flightName?.text = flight.callsign
        depAirportName?.text = flight.estDepartureAirport
        arrAirportName?.text = flight.estArrivalAirport
        depDate?.text = Utils.timestampToString(flight.firstSeen * 1000L)
        arrDate?.text = Utils.timestampToString(flight.lastSeen * 1000L)
        depHour?.text = Utils.timestampToHourMinute(flight.firstSeen * 1000L)
        arrHour?.text = Utils.timestampToHourMinute(flight.lastSeen * 1000L)
        flightTime?.text = Utils.formatFlightDuration(flight.lastSeen - flight.firstSeen)
        if(position%2==0)
            lineareLiyoutPrincipal?.setBackgroundColor(Color.parseColor("#e5f3f5"))
        else
            lineareLiyoutPrincipal?.setBackgroundColor(Color.parseColor("#FFFFFF"))
    }


    private fun initLayout() {
        LayoutInflater.from(context).inflate(R.layout.flight_cell, this, true)
        bindViews()
    }
}