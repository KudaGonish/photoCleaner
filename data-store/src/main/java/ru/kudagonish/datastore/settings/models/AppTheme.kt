package ru.kudagonish.datastore.settings.models

sealed interface AppTheme {
    data object Light : AppTheme
    data object Dark : AppTheme
    data object System : AppTheme
}