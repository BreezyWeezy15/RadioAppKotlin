package com.app.and.states

import com.app.and.models.countries.CountriesModel

sealed class CountryStates {
    data object INITIAL : CountryStates()
    data object LOADING : CountryStates()
    data class SUCCESS(var countriesModel: CountriesModel) : CountryStates()
    data class ERROR(var error : String) : CountryStates()
}