package com.deepzub.footify.presentation.iconic_goal

import com.deepzub.footify.presentation.iconic_goal.model.Goal

data class IconicGoalState(
    val goal: Goal? = null,
    val userGuess: String = "",
    val isCorrect: Boolean = false,
    val isGameOver: Boolean = false,
    val showHelp: Boolean = false
)