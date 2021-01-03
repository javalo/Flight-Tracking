package com.example.fight_tracking

import android.R
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView


class FlightListRecyclerAdapter : RecyclerView.Adapter<FlightListRecyclerAdapter.MyViewHolder>() {

    var flightList : List<FlightModel>?  = null
    var onItemClickListener : OnItemClickListener? = null



    interface OnItemClickListener{
        fun onItemClicked(flightName: String, depart: String,arrive: String)
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.i("RECYCLER", "onCreateViewHolder")

        return MyViewHolder(FlightInfoCell(parent.context))

    }

    override fun getItemCount(): Int {
        return flightList!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i("RECYCLER", "onBindViewHolder")

        val myCell = holder.itemView as FlightInfoCell
        myCell.bindData(flightList!![position], position)
        myCell.setOnClickListener { onItemClickListener?.onItemClicked(
                flightList!![position].callsign,
                flightList!![position].estDepartureAirport,
                flightList!![position].estArrivalAirport) }
    }
}