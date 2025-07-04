package com.deepzub.footify.presentation.who_are_ya.model

data class GuessAttribute(
    val label: String,
    val value: String,
    val isCorrect: Boolean,
    val isImage: Boolean = false,
    val correctValue: String? = null // yaştaki ok için
)