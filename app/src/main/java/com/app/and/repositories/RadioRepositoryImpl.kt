package com.app.and.repositories

import com.app.and.models.countries.CountriesModel
import com.app.and.models.stations.StationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RadioRepositoryImpl {

    suspend fun countries() : Flow<CountriesModel>
    suspend fun stations(country : String) : Flow<StationModel>
}