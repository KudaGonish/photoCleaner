package ru.kudagonish.datastore.settings.models

sealed interface DeletionType {
    data object Instant : DeletionType
    data object SystemTrash : DeletionType
}

internal fun String.mapToDeletionType() = when (this) {
    DeletionType.Instant.toString() -> DeletionType.Instant
    DeletionType.SystemTrash.toString() -> DeletionType.SystemTrash
    else -> throw IllegalArgumentException("Unknown deletion type: $this")
}