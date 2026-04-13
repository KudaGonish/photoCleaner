package ru.kudagonish.datastore.settings

import kotlinx.coroutines.flow.Flow
import ru.kudagonish.datastore.settings.models.AppTheme
import ru.kudagonish.datastore.settings.models.ApplicationSettings
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.datastore.settings.models.FullSettings
import ru.kudagonish.datastore.settings.models.Language
import ru.kudagonish.datastore.settings.models.WorkAlgorithm

interface DataStoreSettings {
    val settingsFlow: Flow<ApplicationSettings>
    suspend fun getSettings(): ApplicationSettings

    suspend fun setSystemLanguage(value: Language)
    suspend fun changeLanguage(newValue: Language)
    suspend fun changeTheme(newValue: AppTheme)
    suspend fun changAlgorithm(newValue: WorkAlgorithm)
    suspend fun changeDeletionType(newValue: DeletionType)


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
                DeletionType.Instant,
                DeletionType.SystemTrash
            )
        )
    }
}