package ru.kudagonish.datastore.settings.models

sealed interface Language {
    data object Ru : Language
    data object Eng : Language
}