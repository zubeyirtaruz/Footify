package com.deepzub.footify.presentation.who_are_ya

import com.deepzub.footify.domain.model.Country

data class CountryState(
    val countries: List<Country> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)