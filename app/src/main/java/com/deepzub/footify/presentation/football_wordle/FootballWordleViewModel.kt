package com.deepzub.footify.presentation.football_wordle

import androidx.lifecycle.ViewModel
import com.deepzub.footify.presentation.football_wordle.model.FOOTBALL_WORDS
import com.deepzub.footify.presentation.football_wordle.model.LetterStatus
import com.deepzub.footify.presentation.football_wordle.model.TileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FootballWordleViewModel @Inject constructor() : ViewModel() {

    private val _ui = MutableStateFlow(WordleUiState())
    val ui: StateFlow<WordleUiState> = _ui

    private var secret = FOOTBALL_WORDS.random()

    init {
        pickSecretWord()
    }

    private fun pickSecretWord() {
        secret = FOOTBALL_WORDS.random()
        println("Footballer: $secret")
    }

    fun onEvent(evt: WordleEvent) = when (evt) {
        is WordleEvent.KeyPress -> addLetter(evt.char)
        WordleEvent.Delete      -> removeLetter()
        WordleEvent.Enter       -> checkWord()
        WordleEvent.Reset       -> reset()
    }

    /* ---------------- internal helpers ---------------- */
    private fun addLetter(c: Char) = _ui.update { s ->
        if (s.currentCol >= 5 || s.gameOver) s
        else {
            val row  = s.currentRow
            val col  = s.currentCol
            val newB = s.board.mapIndexed { r, rowList: List<TileState> ->
                if (r != row) rowList
                else {
                    val newRow = rowList.toMutableList()
                    newRow[col] = TileState(c, LetterStatus.EMPTY)
                    newRow
                }
            }
            s.copy(board = newB, currentCol = col + 1)
        }
    }

    private fun removeLetter() = _ui.update { s ->
        if (s.currentCol <= 0 || s.gameOver) s
        else {
            val row = s.currentRow
            val col = s.currentCol - 1

            val newB = s.board.mapIndexed { r, rowList: List<TileState> ->
                if (r != row) rowList
                else {
                    val newRow = rowList.toMutableList()
                    newRow[col] = TileState()
                    newRow
                }
            }

            s.copy(board = newB, currentCol = col)
        }
    }

    private fun checkWord() {

        _ui.update { s ->
            if (s.currentCol < 5 || s.gameOver) return@update s

            val guess = s.board[s.currentRow].joinToString("") { it.letter.toString() }

            if (guess !in FOOTBALL_WORDS) {
                return@update s.copy(message = "Not in list")
            }

            val statusRow = guess.mapIndexed { i, c ->
                when {
                    c == secret[i] -> LetterStatus.CORRECT
                    c in secret    -> LetterStatus.PRESENT
                    else           -> LetterStatus.ABSENT
                }
            }

            val newB = s.board.mapIndexed { r, rowList: List<TileState> ->
                if (r != s.currentRow) rowList
                else rowList.mapIndexed { i, tile: TileState ->
                    tile.copy(status = statusRow[i])
                }
            }

            val finished = (guess == secret) || s.currentRow == 5

            s.copy(
                board = newB,
                currentRow = s.currentRow + 1,
                currentCol = 0,
                gameOver = finished,
                message = if (finished) "Answer: $secret" else null
            )
        }
    }

    private fun reset() {
        val newSecret = FOOTBALL_WORDS.random()
        println("New Footballer: $newSecret")
        secret = newSecret

        _ui.value = WordleUiState()
    }

}
