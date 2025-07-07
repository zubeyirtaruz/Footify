package com.deepzub.footify.presentation.football_wordle

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
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

    val context = LocalContext.current

    LaunchedEffect(state.gameOver) {
        if (state.gameOver) {
            Toast.makeText(
                context,
                "Answer: ${state.message ?: "Unknown"}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

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

        Row(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .height(36.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (state.currentRow >= 2) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(state.secretFlagUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = state.secretCountry,
                    modifier = Modifier
                        .height(32.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text  = state.secretCountry,
                    color = Color.DarkGray
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                        .width(48.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text  = "",
                    color = Color.Transparent
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))


        /* --- BOARD --- */
        val wordLen = state.board.first().size
        state.board.forEach { row: List<TileState> ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(vertical = 3.dp)
            ) {
                row.forEachIndexed { index, tile ->
                    Tile(tile = tile, wordLength = wordLen, indexInRow = index)
                }
            }
        }

        Spacer(Modifier.height(50.dp))

        /* --- KEYBOARD --- */
        Keyboard(
            letterStat = state.letterStat,
            onKey = { viewModel.onEvent(WordleEvent.KeyPress(it)) },
            onDelete = { viewModel.onEvent(WordleEvent.Delete) },
            onEnter  = { viewModel.onEvent(WordleEvent.Enter) }
        )

    }

    if (showHelp) {
        FootballWordleHelpDialog (onDismiss = { showHelp = false })
    }

}