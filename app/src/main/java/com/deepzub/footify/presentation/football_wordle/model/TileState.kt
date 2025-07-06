package com.deepzub.footify.presentation.football_wordle.model

data class TileState(
    val letter: Char = ' ',
    val status: LetterStatus = LetterStatus.EMPTY
)