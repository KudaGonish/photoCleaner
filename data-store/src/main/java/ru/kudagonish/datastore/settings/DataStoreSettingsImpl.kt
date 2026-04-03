package ru.kudagonish.datastore.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.kudagonish.datastore.settings.models.AppTheme
import ru.kudagonish.datastore.settings.models.ApplicationSettings
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.datastore.settings.models.Language
import ru.kudagonish.datastore.settings.models.WorkAlgorithm
import ru.kudagonish.datastore.settings.models.mapToAppTheme
import ru.kudagonish.datastore.settings.models.mapToDeletionType
import ru.kudagonish.datastore.settings.models.mapToLanguage
import ru.kudagonish.datastore.settings.models.mapToWorkAlgorithm

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

internal class DataStoreSettingsImpl(private val context: Context) : DataStoreSettings {
    val defaultSettings = ApplicationSettings(
        theme = AppTheme.System,
        language = Language.Ru,
        algorithm = WorkAlgorithm.DayMoth,
        deletionType = DeletionType.Deffered(ApplicationSettings.DEFAULT_DEFFERED_DAYS)
    )

    override val settingsFlow: Flow<ApplicationSettings> =
        context.dataStore.data.map { preferences -> preferences.createSettingsModel() }

    override suspend fun getSettings(): ApplicationSettings {
        return settingsFlow.first()
    }

    override suspend fun changeLanguage(newValue: Language) {
        context.dataStore.edit { preferences ->
            preferences[languageKey] = newValue.toString()
        }
    }

    override suspend fun changeTheme(newValue: AppTheme) {
        context.dataStore.edit { preferences ->
            preferences[themeKey] = newValue.toString()
        }
    }

    override suspend fun changAlgorithm(newValue: WorkAlgorithm) {
        context.dataStore.edit { preferences ->
            preferences[algorithmKey] = newValue.toString()
        }
    }

    override suspend fun changeDeletionType(newValue: DeletionType) {
        TODO("Not yet implemented")
    }

    private fun Preferences.createSettingsModel(): ApplicationSettings {
        val deletionDays = this[deletionDaysKey]
            ?.toInt() ?: ApplicationSettings.DEFAULT_DEFFERED_DAYS

        val deletionType = this[deletionTypeKey]
            ?.mapToDeletionType(deletionDays) ?: defaultSettings.deletionType

        return ApplicationSettings(
            theme = this[themeKey]?.mapToAppTheme() ?: defaultSettings.theme,
            language = this[languageKey]?.mapToLanguage() ?: defaultSettings.language,
            algorithm = this[algorithmKey]?.mapToWorkAlgorithm()
                ?: defaultSettings.algorithm,
            deletionType = deletionType
        )
    }

    private companion object {
        val themeKey = stringPreferencesKey("theme")
        val languageKey = stringPreferencesKey("language")
        val algorithmKey = stringPreferencesKey("algorithm")
        val deletionTypeKey = stringPreferencesKey("deletionType")
        val deletionDaysKey = stringPreferencesKey("deletionDays")
    }
}