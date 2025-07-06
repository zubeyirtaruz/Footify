package com.deepzub.footify.presentation.football_wordle

sealed class WordleEvent {
    data class KeyPress(val char: Char) : WordleEvent()
    data object Delete : WordleEvent()
    data object Enter  : WordleEvent()
    data object Reset  : WordleEvent()
}