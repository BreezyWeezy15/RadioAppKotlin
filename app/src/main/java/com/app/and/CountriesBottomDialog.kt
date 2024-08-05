package com.app.and

import android.app.Dialog
import android.os.Bundle
import android.os.health.UidHealthStats
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.and.adapters.CountriesAdapter
import com.app.and.databinding.CountriesDialogLayoutBinding
import com.app.and.listeners.CountryListener
import com.app.and.models.countries.CountriesModel
import com.app.and.models.countries.CountriesModelItem
import com.app.and.states.CountryStates
import com.app.and.viewmodels.RadioViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CountriesBottomDialog : BottomSheetDialogFragment() {

    private val radioViewModel : RadioViewModel by viewModel()
    private lateinit var countriesAdapter: CountriesAdapter
    private lateinit var binding : CountriesDialogLayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         binding = CountriesDialogLayoutBinding.inflate(inflater,container,false)
         return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.setHasFixedSize(true)

        radioViewModel.getCountries()
        lifecycleScope.launch {
            radioViewModel.countriesFlow.collectLatest {
                when(it){
                    is CountryStates.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is CountryStates.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        countriesAdapter = CountriesAdapter(it.countriesModel,object : CountryListener {
                            override fun onCountrySelected(country: String) {
                                Utils.saveCountry(requireContext(),country)
                                Utils.setTime(requireContext(),false)
                                findNavController().navigate(R.id.action_countriesFragment_to_homeFragment)
                            }

                        })
                        binding.rv.adapter = countriesAdapter
                    }
                    is CountryStates.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Log.d("Country Tag",it.error)
                    }
                    else -> {}
                }
            }
        }


    }

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            dialog.setCanceledOnTouchOutside(false)
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                sheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.isDraggable = false
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.FullScreenBottomSheetDialog)
    }
}