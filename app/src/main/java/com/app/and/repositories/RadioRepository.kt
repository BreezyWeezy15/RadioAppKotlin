package com.app.and.repositories

import com.app.and.models.countries.CountriesModel
import com.app.and.models.stations.StationModel
import com.app.and.services.AuthService
import com.blongho.country_data.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RadioRepository (
    private val authService: AuthService
) : RadioRepositoryImpl {

    override suspend fun countries(): Flow<CountriesModel> {
       return flow {
           emit(authService.getCountries())
       }
    }

    override suspend fun stations(country: String): Flow<StationModel> {
        return flow {
            emit(authService.getStations(country))
        }
    }
}