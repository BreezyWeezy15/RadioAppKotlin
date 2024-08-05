package com.app.and.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.and.repositories.RadioRepository
import com.app.and.states.CountryStates
import com.app.and.states.StationsStates
import com.blongho.country_data.Country
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.ws.RealWebSocket

class RadioViewModel(
    private val radioRepository: RadioRepository
) : ViewModel() {

    private val _countriesFlow : MutableStateFlow<CountryStates>  = MutableStateFlow(CountryStates.INITIAL)
    val countriesFlow : StateFlow<CountryStates> get() = _countriesFlow


    private val _stationsFlow : MutableStateFlow<StationsStates>  = MutableStateFlow(StationsStates.INITIAL)
    val stationsFlow : StateFlow<StationsStates> get() = _stationsFlow


    fun getCountries(){
        viewModelScope.launch {
            try {
                _countriesFlow.value = CountryStates.LOADING
                radioRepository.countries().collectLatest {
                    _countriesFlow.value = CountryStates.SUCCESS(it)
                }
            } catch (ex : Exception){
                _countriesFlow.value = CountryStates.ERROR(ex.message!!)
            }
        }
    }

    fun getStations(country : String){
        viewModelScope.launch {
            try {
                _stationsFlow.value = StationsStates.LOADING
                radioRepository.stations(country).collectLatest {
                    _stationsFlow.value = StationsStates.SUCCESS(it)
                }
            } catch (ex : Exception){
                _stationsFlow.value = StationsStates.ERROR(ex.message!!)
            }
        }
    }

}