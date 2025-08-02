package com.deepzub.footify.presentation.guess_football_club.model

import com.deepzub.footify.domain.model.Club
import java.util.UUID

data class ClubGuessRow(
    val club: Club,
    val attributes: List<ClubGuessAttribute?>,
    val guessId: String = UUID.randomUUID().toString(),
    var animated: Boolean = false
)