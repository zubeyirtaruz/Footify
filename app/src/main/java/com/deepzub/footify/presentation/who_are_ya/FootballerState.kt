package com.deepzub.footify.presentation.who_are_ya

import com.deepzub.footify.domain.model.Footballer

data class FootballerState(
    val isLoading: Boolean = false,
    val footballers: List<Footballer> = emptyList(),
    val error: String = ""
)