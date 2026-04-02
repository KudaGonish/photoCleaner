package ru.kudagonish.datastore.settings.models

sealed interface AppTheme {
    data object Light : AppTheme
    data object Dark : AppTheme
    data object System : AppTheme
}

internal fun String.mapToAppTheme() = when (this) {
    AppTheme.Light.toString() -> AppTheme.Light
    AppTheme.Dark.toString() -> AppTheme.Dark
    AppTheme.System.toString() -> AppTheme.System
    else -> throw IllegalArgumentException("Unknown theme: $this")
}