package ru.kudagonish.photocleaner.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kudagonish.datastore.settings.DataStoreSettings
import ru.kudagonish.datastore.settings.models.Language
import ru.kudagonish.permission_rationale_feature.domain.PermissionRepository
import ru.kudagonish.photocleaner.domain.AppRepository

internal class AppRepositoryImpl(
    private val permissionRepository: PermissionRepository,
    private val dataStoreSettings: DataStoreSettings
) : AppRepository {
    override val languageFlow = dataStoreSettings.settingsFlow
        .map {
            when(it.language){
                Language.Ru -> RU_LOCALE
                Language.Eng -> ENG_LOCALE
            }
        }
        .distinctUntilChanged()

    override val themeFlow = dataStoreSettings.settingsFlow
        .map { it.theme }
        .distinctUntilChanged()

    override fun getRequestPermissionCount(): Flow<Int> {
        return permissionRepository.getRequestPermissionCount()
    }

    override suspend fun setLocale(locale: String) {
        val mappedLocale = when {
            locale.startsWith(RU_LOCALE) -> Language.Ru
            else -> Language.Eng
        }
        dataStoreSettings.setSystemLanguage(mappedLocale)
    }
    companion object {
        const val RU_LOCALE = "ru"
        const val ENG_LOCALE = "en"
    }
}
