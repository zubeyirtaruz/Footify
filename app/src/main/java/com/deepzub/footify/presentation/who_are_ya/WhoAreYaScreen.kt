package com.deepzub.footify.presentation.who_are_ya

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.util.Constants
import com.deepzub.footify.util.ShowToast


@Composable
fun WhoAreYaScreen(
    navController: NavController,
    viewModel: WhoAreYaViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    var photoVisible by remember { mutableStateOf(true) }
    var guessCount by remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
//        viewModel.loadTop5Leagues(Constants.SEASON_ID)
        viewModel.loadFootballers(league = Constants.PREMIER_LEAGUE_ID, season = Constants.SEASON_ID)
    }

    if (state.isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else if (state.error.isNotEmpty()) {
        ShowToast(state.error)
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1B0832)) // Arka plan rengi
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Orta kutu
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (photoVisible) {
                    Text(
                        text = "BIG 5",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Photo",
                        modifier = Modifier.size(100.dp),
                        tint = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Button(
                            onClick = { photoVisible = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF635BFF))
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = "Hide")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Hide Photo")
                        }

                        Button(
                            onClick = { photoVisible = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF635BFF))
                        ) {
                            Icon(Icons.Default.Refresh,
                                contentDescription = "Show")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Show Photo")
                        }
                    }
                } else {
                    // Fotoğraf gizlenmişse sadece soru işareti ve giriş alanı göster

                    var userQuery by remember { mutableStateOf("") }
                    val filteredFootballers = if (userQuery.length >= 2) {
                        viewModel.searchFootballers(userQuery)
                    } else {
                        emptyList()
                    }


                    Text(
                        text = "?",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = userQuery,
                        onValueChange = { userQuery = it },
                        placeholder = {
                            Text("GUESS $guessCount OF 8", color = Color.Gray)
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .border(1.dp, Color.Black, RoundedCornerShape(6.dp))
                    )

                    if (userQuery.length >= 2) {
                        LazyColumn {
                            items(filteredFootballers) { player ->
                                FootballerItem(player)
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun FootballerItem(footballer: Footballer) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF9F9F9)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Team logo
            AsyncImage(
                model = footballer.teamLogo,
                contentDescription = footballer.teamName,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Name and team info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = footballer.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF1B1B1B)
                )
                Text(
                    text = footballer.teamName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // Position badge
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = getPositionShortName(footballer.position),
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF424242)
                )
            }
        }
    }
}

fun getPositionShortName(position: String): String {
    return when (position.lowercase()) {
        "goalkeeper" -> "GK"
        "defender" -> "DF"
        "midfielder" -> "MF"
        "attacker" -> "FW"
        else -> position.take(2).uppercase() // Bilinmeyen pozisyonlar için ilk iki harf
    }
}


