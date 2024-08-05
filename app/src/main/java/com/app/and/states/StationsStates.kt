package com.app.and.states

import com.app.and.models.countries.CountriesModel
import com.app.and.models.stations.StationModel

sealed class StationsStates {
    data object INITIAL : StationsStates()
    data object LOADING : StationsStates()
    data class SUCCESS(var stationModel: StationModel) : StationsStates()
    data class ERROR(var error : String) : StationsStates()
}