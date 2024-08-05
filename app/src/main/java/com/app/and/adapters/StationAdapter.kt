package com.app.and.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.and.R
import com.app.and.databinding.StationsRowsLayoutBinding
import com.app.and.models.stations.StationModel

class StationAdapter(
    private val stationModel: StationModel
) : RecyclerView.Adapter<StationAdapter.ViewHolder>() {

    inner class ViewHolder(var binding : StationsRowsLayoutBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = DataBindingUtil.inflate<StationsRowsLayoutBinding>(LayoutInflater.from(parent.context),
           R.layout.stations_rows_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stationModel.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val station = stationModel[position]
        holder.binding.station = station
    }
}