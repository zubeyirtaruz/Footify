package com.deepzub.footify.presentation.football_wordle

import androidx.lifecycle.ViewModel
import com.deepzub.footify.presentation.football_wordle.model.FOOTBALLER_LIST
import com.deepzub.footify.presentation.football_wordle.model.FootballerInfo
import com.deepzub.footify.presentation.football_wordle.model.LetterStatus
import com.deepzub.footify.presentation.football_wordle.model.TileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FootballWordleViewModel @Inject constructor() : ViewModel() {

    private lateinit var secret: FootballerInfo

    private val _ui = MutableStateFlow(WordleUiState(board = emptyList()))
    val ui: StateFlow<WordleUiState> = _ui

    init {
        reset()
    }

    fun onEvent(evt: WordleEvent) = when (evt) {
        is WordleEvent.KeyPress -> addLetter(evt.char)
        WordleEvent.Delete      -> removeLetter()
        WordleEvent.Enter       -> checkWord()
        WordleEvent.Reset       -> reset()
    }

    /* ---------------- internal helpers ---------------- */

    private fun createBoard(length: Int): List<List<TileState>> =
        List(6) { List(length) { TileState() } }

    private fun addLetter(c: Char) = _ui.update { s ->
        if (s.currentCol >= secret.name.length || s.gameOver) s
        else {
            val row = s.currentRow
            val col = s.currentCol

            val newB = s.board.mapIndexed { r, rowList ->
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
            if (s.currentCol < secret.name.length || s.gameOver) return@update s

            val guess = s.board[s.currentRow].joinToString("") { it.letter.toString() }

            val statusRow = guess.mapIndexed { i, c ->
                when {
                    c == secret.name[i] -> LetterStatus.CORRECT
                    c in secret.name    -> LetterStatus.PRESENT
                    else           -> LetterStatus.ABSENT
                }
            }

            val newB = s.board.mapIndexed { r, rowList: List<TileState> ->
                if (r != s.currentRow) rowList
                else rowList.mapIndexed { i, tile: TileState ->
                    tile.copy(status = statusRow[i])
                }
            }

            val finished = (guess == secret.name) || s.currentRow == 5

            val newLetterStat = s.letterStat.toMutableMap()

            guess.forEachIndexed { i, ch ->
                val newStatus = statusRow[i]
                val oldStatus = newLetterStat[ch]
                if (newStatus.isStrongerThan(oldStatus)) {
                    newLetterStat[ch] = newStatus
                }
            }

            s.copy(
                board = newB,
                currentRow = s.currentRow + 1,
                currentCol = 0,
                gameOver = finished,
                message = if (finished) secret.name else null,
                letterStat  = newLetterStat
            )
        }
    }

    private fun LetterStatus.isStrongerThan(other: LetterStatus?): Boolean {
        return when (this) {
            LetterStatus.CORRECT  -> other != LetterStatus.CORRECT
            LetterStatus.PRESENT  -> other == null || other == LetterStatus.EMPTY || other == LetterStatus.ABSENT
            LetterStatus.ABSENT   -> other == null || other == LetterStatus.EMPTY
            LetterStatus.EMPTY    -> false
        }
    }

    private fun reset() {
        secret = FOOTBALLER_LIST.random()
        println("New Footballer: ${secret.name}, ${secret.country}")

        _ui.value = WordleUiState(
            board = createBoard(secret.name.length),
            currentRow = 0,
            currentCol = 0,
            gameOver = false,
            message = null,
            letterStat = emptyMap(),
            secretName = secret.name,
            secretCountry = secret.country,
            secretFlagUrl = secret.flagUrl
        )
    }

}
