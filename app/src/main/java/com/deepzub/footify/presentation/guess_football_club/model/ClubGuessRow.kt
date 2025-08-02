package com.deepzub.footify.presentation.guess_football_club.model

import com.deepzub.footify.domain.model.Club

data class ClubGuessRow(
    val club: Club,
    val attributes: List<ClubGuessAttribute?>,
    var animated: Boolean = false
)