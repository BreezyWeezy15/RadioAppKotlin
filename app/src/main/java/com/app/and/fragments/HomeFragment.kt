package com.app.and.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.and.R
import com.app.and.Utils
import com.app.and.adapters.StationAdapter
import com.app.and.databinding.HomeFragmentLayoutBinding
import com.app.and.listeners.PlayListener
import com.app.and.states.StationsStates
import com.app.and.viewmodels.RadioViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private val radioViewModel : RadioViewModel by viewModel()
   private lateinit var stationAdapter: StationAdapter
    private lateinit var binding  : HomeFragmentLayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeFragmentLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bottomSheetBehavior = BottomSheetBehavior.from(binding.sheet)
        bottomSheetBehavior.peekHeight = 0


        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.setHasFixedSize(true)

        radioViewModel.getStations(Utils.getCountry(requireContext())!!)
        lifecycleScope.launch {
            radioViewModel.stationsFlow.collectLatest {
                when(it){
                    is StationsStates.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is StationsStates.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        stationAdapter = StationAdapter(it.stationModel,object : PlayListener {
                            override fun onPlay(url: String) {
                                bottomSheetBehavior.peekHeight = 100
                                playAudio(url)
                            }
                        })
                        binding.rv.adapter = stationAdapter
                    }
                    is StationsStates.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    else -> {}
                }
            }
        }

        
    }

    private fun playAudio(url: String) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        } else {
            mediaPlayer?.reset()
        }

        try {
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.setOnPreparedListener {
                it.start()
            }
            mediaPlayer?.setOnErrorListener { mp, what, extra ->
                // Handle errors appropriately
                true // Return true to indicate that we handled the error
            }
            mediaPlayer?.prepareAsync()
            binding.playPauseBtn.setImageResource(R.drawable.pause)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}