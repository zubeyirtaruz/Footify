package com.deepzub.footify.presentation.football_wordle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deepzub.footify.R
import com.deepzub.footify.presentation.football_wordle.components.FootballWordleHelpDialog
import com.deepzub.footify.presentation.football_wordle.components.FootballWordleTopBar
import com.deepzub.footify.presentation.football_wordle.components.Keyboard
import com.deepzub.footify.presentation.football_wordle.components.Tile
import com.deepzub.footify.presentation.football_wordle.model.TileState

@Composable
fun FootballWordleScreen(
    navController: NavController,
    viewModel: FootballWordleViewModel = hiltViewModel()
) {
    val state by viewModel.ui.collectAsState()
    var showHelp by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        FootballWordleTopBar (
            onResetClick = {
                viewModel.onEvent(WordleEvent.Reset)
            },
            onHelpClick = { showHelp = true }
        )

        Spacer(modifier = Modifier.height(30.dp))


        /* --- BOARD --- */
        state.board.forEach { row: List<TileState> ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(vertical = 3.dp)
            ) {
                row.forEach { tile: TileState ->
                    Tile(tile)
                }
            }
        }

        Spacer(Modifier.height(50.dp))

        /* --- KEYBOARD --- */
        Keyboard(
            onKey = { viewModel.onEvent(WordleEvent.KeyPress(it)) },
            onDelete = { viewModel.onEvent(WordleEvent.Delete) },
            onEnter  = { viewModel.onEvent(WordleEvent.Enter) }
        )

    }

    if (showHelp) {
        FootballWordleHelpDialog (onDismiss = { showHelp = false })
    }

}