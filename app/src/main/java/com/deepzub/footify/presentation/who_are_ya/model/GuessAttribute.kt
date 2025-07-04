package com.deepzub.footify.presentation.who_are_ya.model

data class GuessAttribute(
    val type: AttributeType,
    val value: String,
    val isCorrect: Boolean,
    val isImage: Boolean = false,
    val correctValue: String? = null
) {
    val label: String get() = type.label
}