package ru.kudagonish.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.kudagonish.datastore.settings.models.DeletionType

internal class DataStoreManagerImpl(private val context: Context) : DataStoreManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

    override suspend fun saveValue(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override fun getValue(key: String, defaultValue: String): Flow<String> {
        val preferencesKey = stringPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: defaultValue
        }
    }

    override suspend fun getValueSync(key: String, defaultValue: String): String {
        return getValue(key, defaultValue).first()
    }
}
