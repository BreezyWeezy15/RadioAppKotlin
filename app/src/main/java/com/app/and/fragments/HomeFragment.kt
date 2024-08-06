package com.app.and.fragments

import android.Manifest
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.PendingIntentCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.and.MainActivity
import com.app.and.R
import com.app.and.Utils
import com.app.and.adapters.StationAdapter
import com.app.and.databinding.HomeFragmentLayoutBinding
import com.app.and.listeners.PlayListener
import com.app.and.listeners.RadioListener
import com.app.and.services.RadioService
import com.app.and.states.StationsStates
import com.app.and.viewmodels.RadioViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() , RadioListener {


    private var playPauseBtn : Int = R.drawable.play
    private lateinit var radioService: RadioService
    private var mBound: Boolean = false
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private val radioViewModel : RadioViewModel by viewModel()
    private lateinit var stationAdapter: StationAdapter
    private lateinit var binding  : HomeFragmentLayoutBinding


    private val serviceConnection = object  : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service : IBinder?) {
            val binder = service as RadioService.RadioBinder
            radioService = binder.getRadioService()
            radioService.radioListener = this@HomeFragment
            mBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mBound = false
        }

    }


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
                            override fun onPlay(country: String , station : String , url : String) {
                                bottomSheetBehavior.peekHeight = 100
                                createNotification(country,station)
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

        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
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
    private fun createNotification(country : String , station : String){

        if (!mBound){
            bindRadioService()
            return
        }

        // PENDING INTENT PLAY
        val playIntent = Intent(requireContext(),RadioService::class.java)
        playIntent.action = Utils.PLAY_ACTION

        val playPendingIntent = PendingIntent.getActivity(requireContext(),1,playIntent,
            PendingIntent.FLAG_IMMUTABLE)

        // PENDING INTENT PAUSE
        val pauseIntent = Intent(requireContext(),RadioService::class.java)
        playIntent.action = Utils.PAUSE_ACTION

        val pausePendingIntent = PendingIntent.getActivity(requireContext(),1,pauseIntent,
            PendingIntent.FLAG_IMMUTABLE)


        if (mediaPlayer?.isPlaying == true){
              playPauseBtn = R.drawable.pause
        } else {
              playPauseBtn = R.drawable.play
        }


        val notificationCompat = NotificationCompat.Builder(requireContext(),Utils.NOTIFICATION_ID)
            .setContentTitle(country)
            .setContentText(station)
            .setSmallIcon(R.drawable.notification)
            .addAction(playPauseBtn,"Play",playPendingIntent)
            .addAction(playPauseBtn,"Pause",pausePendingIntent)
            .setOngoing(true)
            .build()


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    Utils.POST_NOTIFICATION_REQUEST_CODE)
                return
            }
        }

        NotificationManagerCompat.from(requireContext()).notify(1,notificationCompat)
    }
    private fun bindRadioService() {
        val radioService = Intent(requireContext(),RadioService::class.java)
        requireContext().bindService(radioService,serviceConnection,Context.BIND_AUTO_CREATE)
    }

    override fun onRadioPlay() {
        mediaPlayer?.start()
    }

    override fun onRadioPause() {
       if (mediaPlayer?.isPlaying == true){
           mediaPlayer!!.pause()
       }
    }


}