package ru.kudagonish.datastore.settings.models

sealed interface WorkAlgorithm {
    data object DayMoth : WorkAlgorithm
    data object FullTime : WorkAlgorithm
}

internal fun String.mapToWorkAlgorithm() = when (this) {
    WorkAlgorithm.DayMoth.toString() -> WorkAlgorithm.DayMoth
    WorkAlgorithm.FullTime.toString() -> WorkAlgorithm.FullTime
    else -> throw IllegalArgumentException("Unknown algorithm: $this")
}