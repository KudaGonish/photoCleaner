package ru.kudagonish.datastore.settings.models

sealed interface DeletionType {
    data object Instant : DeletionType
    data class Deffered(val days: Int) : DeletionType {
        override fun toString(): String {
            return this::class.java.simpleName
        }
    }

    data object SystemTrash : DeletionType
}

internal fun String.mapToDeletionType(defferedDays: Int) = when (this) {
    DeletionType.Instant.toString() -> DeletionType.Instant
    DeletionType.SystemTrash.toString() -> DeletionType.SystemTrash
    DeletionType.Deffered(0).toString() -> DeletionType.Deffered(defferedDays)
    else -> throw IllegalArgumentException("Unknown deletion type: $this")
}