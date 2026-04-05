package ru.kudagonish.feature_settings.data

import kotlinx.coroutines.flow.Flow
import ru.kudagonish.datastore.settings.DataStoreSettings
import ru.kudagonish.datastore.settings.models.AppTheme
import ru.kudagonish.datastore.settings.models.ApplicationSettings
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.datastore.settings.models.Language
import ru.kudagonish.datastore.settings.models.WorkAlgorithm
import ru.kudagonish.feature_settings.domain.SettingsRepository

internal class SettingsRepositoryImpl(
    private val dataStoreSettings: DataStoreSettings
) : SettingsRepository {
    override val settingsFlow: Flow<ApplicationSettings> = dataStoreSettings.settingsFlow

    override suspend fun changeLanguage(newValue: Language) =
        dataStoreSettings.changeLanguage(newValue)

    override suspend fun changeTheme(newValue: AppTheme) =
        dataStoreSettings.changeTheme(newValue)

    override suspend fun changAlgorithm(newValue: WorkAlgorithm) =
        dataStoreSettings.changAlgorithm(newValue)

    override suspend fun changeDeletionType(newValue: DeletionType) =
        dataStoreSettings.changeDeletionType(newValue)
}
