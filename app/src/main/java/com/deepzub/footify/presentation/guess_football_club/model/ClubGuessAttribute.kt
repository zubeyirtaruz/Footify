package com.deepzub.footify.presentation.guess_football_club.model

data class  ClubGuessAttribute(
    val type: ClubAttributeType,
    val value: String,
    val isCorrect: Boolean,
    val isImage: Boolean = false,
    val correctValue: String? = null
) {
    val label: String get() = type.label
}