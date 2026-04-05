package ru.kudagonish.photocleaner.domain

import kotlinx.coroutines.flow.Flow
import ru.kudagonish.datastore.settings.models.AppTheme

internal interface AppRepository {
    val languageFlow: Flow<String>
    val themeFlow: Flow<AppTheme>

    fun getRequestPermissionCount(): Flow<Int>
    suspend fun setLocale(locale: String)
}