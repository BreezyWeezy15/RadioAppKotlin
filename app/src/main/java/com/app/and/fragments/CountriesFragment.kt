package com.app.and.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.and.CountriesBottomDialog
import com.app.and.databinding.CountriesFragmentLayoutBinding

class CountriesFragment : Fragment() {

    private lateinit var binding : CountriesFragmentLayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CountriesFragmentLayoutBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countriesBottomDialog = CountriesBottomDialog()
        countriesBottomDialog.show(childFragmentManager,countriesBottomDialog.tag)
    }
}