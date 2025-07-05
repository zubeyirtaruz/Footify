package com.deepzub.footify.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.deepzub.footify.presentation.navigation.AppNavGraph
import com.deepzub.footify.presentation.ui.theme.FootifyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FootifyTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}