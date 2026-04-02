package ru.kudagonish.datastore.settings.models

internal data class DefaultSettings(
    val theme: AppTheme = AppTheme.System,
    val language: Language = Language.Ru,
    val algorithm: WorkAlgorithm = WorkAlgorithm.DayMoth,
    val deletionType: DeletionType = DeletionType.Deffered(3)
)