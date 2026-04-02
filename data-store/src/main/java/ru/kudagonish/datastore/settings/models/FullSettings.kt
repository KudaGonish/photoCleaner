package ru.kudagonish.datastore.settings.models

data class FullSettings(
    val themes: List<AppTheme>,
    val languages: List<Language>,
    val algorithms: List<WorkAlgorithm>,
    val deletionTypes: List<DeletionType>
)