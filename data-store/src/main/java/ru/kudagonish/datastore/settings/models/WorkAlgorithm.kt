package ru.kudagonish.datastore.settings.models

sealed interface WorkAlgorithm {
    data object DayMoth : WorkAlgorithm
    data object FullTime : WorkAlgorithm
}