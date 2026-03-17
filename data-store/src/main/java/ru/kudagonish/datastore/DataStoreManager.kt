package ru.kudagonish.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun saveValue(key: String, value: String)
    fun getValue(key: String, defaultValue: String): Flow<String>
    suspend fun getValueSync(key: String, defaultValue: String): String
}
