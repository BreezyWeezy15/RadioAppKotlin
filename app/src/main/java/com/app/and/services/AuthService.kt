package com.app.and.services

import com.app.and.models.countries.CountriesModel
import com.app.and.models.stations.StationModel
import com.blongho.country_data.Country
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {


    @Headers("x-rapidapi-key:dce54b5e9amsh271d5af53607ac9p1803fajsncd16f45b65f0")
    @GET("/countries")
    suspend fun getCountries() : CountriesModel

    @Headers("x-rapidapi-key:dce54b5e9amsh271d5af53607ac9p1803fajsncd16f45b65f0")
    @GET("stations/bycountry/{country}?hidebroken=true&offset=0&limit=10'")
    suspend fun getStations(
        @Path("country") country: String
    ) : StationModel
}