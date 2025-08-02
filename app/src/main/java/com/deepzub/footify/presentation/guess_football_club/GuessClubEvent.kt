package com.deepzub.footify.presentation.guess_football_club

import com.deepzub.footify.domain.model.Club

sealed interface GuessClubEvent {
    data object InitData : GuessClubEvent
    data object ResetGame : GuessClubEvent
    data class TogglePhoto(val visible: Boolean) : GuessClubEvent
    data class MakeGuess(val club: Club) : GuessClubEvent
}