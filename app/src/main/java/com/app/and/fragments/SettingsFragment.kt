package com.app.and.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.and.CountriesBottomDialog
import com.app.and.Utils
import com.app.and.databinding.SettingsFragmentLayoutBinding

class SettingsFragment : Fragment() {

    private lateinit var binding : SettingsFragmentLayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SettingsFragmentLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (Utils.getMode(requireContext())){
            binding.modeTxt.text = "Dark Mode"
        } else {
            binding.modeTxt.text = "Light Mode"
        }
        /// Check for mode
        binding.switchBtn.isChecked = Utils.getMode(requireContext())



        binding.language.setOnClickListener {
            val countriesBottomDialog = CountriesBottomDialog()
            countriesBottomDialog.show(childFragmentManager,countriesBottomDialog.tag)
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.switchBtn.setOnCheckedChangeListener { compoundButton, isChecked ->
            Utils.setMode(requireContext(),isChecked)
            if (isChecked){
                Utils.enabledDarkMode()
            } else {
                Utils.disableDarkMode()
            }
        }
    }
}