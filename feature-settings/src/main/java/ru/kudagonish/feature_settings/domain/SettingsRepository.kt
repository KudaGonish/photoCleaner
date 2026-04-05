package ru.kudagonish.feature_settings.domain

import kotlinx.coroutines.flow.Flow
import ru.kudagonish.datastore.settings.models.AppTheme
import ru.kudagonish.datastore.settings.models.ApplicationSettings
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.datastore.settings.models.Language
import ru.kudagonish.datastore.settings.models.WorkAlgorithm

internal interface SettingsRepository {
    val settingsFlow: Flow<ApplicationSettings>

    suspend fun changeLanguage(newValue: Language)
    suspend fun changeTheme(newValue: AppTheme)
    suspend fun changAlgorithm(newValue: WorkAlgorithm)
    suspend fun changeDeletionType(newValue: DeletionType)
}
