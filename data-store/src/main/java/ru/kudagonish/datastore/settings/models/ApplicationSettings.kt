package ru.kudagonish.datastore.settings.models

data class ApplicationSettings(
    val theme: AppTheme ,
    val language: Language,
    val algorithm: WorkAlgorithm,
    val deletionType: DeletionType
){
    companion object {
        const val DEFAULT_DEFFERED_DAYS = 3
    }
}