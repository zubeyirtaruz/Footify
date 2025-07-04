package com.deepzub.footify.presentation.who_are_ya.model

import com.deepzub.footify.domain.model.Footballer

data class GuessRow(
    val footballer: Footballer,
    val attributes: List<GuessAttribute?>
)