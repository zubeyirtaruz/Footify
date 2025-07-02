package com.deepzub.footify.presentation.who_are_ya

import com.deepzub.footify.domain.model.Footballer

data class FootballerState(
    val footballers: List<Footballer> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)