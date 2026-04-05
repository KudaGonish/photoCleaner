package ru.kudagonish.datastore.settings.models

sealed interface Language {
    data object Ru : Language
    data object Eng : Language
}

internal fun String.mapToLanguage() = when (this) {
    Language.Ru.toString() -> Language.Ru
    Language.Eng.toString() -> Language.Eng
    else -> throw IllegalArgumentException("Unknown language: $this")
}