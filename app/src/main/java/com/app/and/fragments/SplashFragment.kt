package com.app.and.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.and.R
import com.app.and.Utils
import com.app.and.databinding.SplashFragmentLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var coroutineScope : CoroutineScope
    private lateinit var job: Job
    private lateinit var binding : SplashFragmentLayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SplashFragmentLayoutBinding.inflate(inflater,container,false)
        coroutineScope = CoroutineScope(Dispatchers.Main)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        job = coroutineScope.launch {
            delay(5000)
            if (Utils.isFirstTime(requireContext())){
                findNavController().navigate(R.id.action_splashFragment_to_countriesFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }

    }

    override fun onDestroy() {
        job.cancel("")
        super.onDestroy()
    }
}