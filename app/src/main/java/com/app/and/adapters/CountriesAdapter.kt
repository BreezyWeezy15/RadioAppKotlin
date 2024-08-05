package com.app.and.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.and.R
import com.app.and.databinding.CountriesRowsLayoutBinding
import com.app.and.listeners.CountryListener
import com.app.and.models.countries.CountriesModel
import com.blongho.country_data.World

class CountriesAdapter(
    private val countriesModel: CountriesModel,
    private val countryListener: CountryListener
) : RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {


    inner class ViewHolder(var binding : CountriesRowsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DataBindingUtil.inflate<CountriesRowsLayoutBinding>(LayoutInflater.from(parent.context),
            R.layout.countries_rows_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countriesModel.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countriesModel[position]
        holder.binding.countryItem = country
        holder.binding.listener = countryListener
        holder.binding.flagImg.setImageResource(World.getFlagOf(country.iso_3166_1))
    }
}