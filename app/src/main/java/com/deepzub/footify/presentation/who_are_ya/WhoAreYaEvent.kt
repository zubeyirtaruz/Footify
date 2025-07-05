package com.deepzub.footify.presentation.who_are_ya

import com.deepzub.footify.domain.model.Footballer

sealed interface WhoAreYaEvent {
    data object InitData : WhoAreYaEvent
    data object ResetGame : WhoAreYaEvent
    data class TogglePhoto(val visible: Boolean) : WhoAreYaEvent
    data class MakeGuess(val footballer: Footballer) : WhoAreYaEvent
}
