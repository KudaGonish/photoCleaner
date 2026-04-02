package ru.kudagonish.datastore.settings.models

sealed interface DeletionType {
    data object Instant : DeletionType
    data class Deffered(val days: Int) : DeletionType
    data object SystemTrash : DeletionType
}