package ru.kudagonish.datastore.settings

import ru.kudagonish.datastore.settings.models.AppTheme
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.datastore.settings.models.FullSettings
import ru.kudagonish.datastore.settings.models.Language
import ru.kudagonish.datastore.settings.models.WorkAlgorithm

interface DataStoreSettings {
    suspend fun getSettings()

    fun changeTheme(newValue: AppTheme)
    fun changeLanguage(newValue: Language)
    fun changAlgorithm(newValue: WorkAlgorithm)
    fun changeDeletionType(newValue: DeletionType)


    companion object {
        val defaultSettings = FullSettings(
            themes = listOf(
                AppTheme.Light,
                AppTheme.Dark,
                AppTheme.System
            ),
            languages = listOf(
                Language.Ru,
                Language.Eng
            ),
            algorithms = listOf(
                WorkAlgorithm.DayMoth,
                WorkAlgorithm.FullTime
            ),
            deletionTypes = listOf(
                DeletionType.Instant, DeletionType.Deffered(3),
                DeletionType.SystemTrash
            )
        )
    }
}