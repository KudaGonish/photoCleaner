package ru.kudagonish.permission_rationale.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kudagonish.datastore.DataStoreManager
import ru.kudagonish.permission_rationale.domain.PermissionRepository

internal class PermissionRepositoryImpl(
    private val dataStoreManager: DataStoreManager
) : PermissionRepository {

    override fun getRequestPermissionCount(): Flow<Int> = dataStoreManager
        .getValue(COUNTER_KEY, DEFAULT_VALUE)
        .map { it.toInt() }

    override suspend fun updateRequestCount(count: Int) {
        dataStoreManager.saveValue(COUNTER_KEY, count.toString())
    }

    override suspend fun resetRequestCount() {
        dataStoreManager.saveValue(COUNTER_KEY, DEFAULT_VALUE)
    }

    private companion object {
        const val COUNTER_KEY = "requestPermissionCountKey"
        const val DEFAULT_VALUE = "0"
    }
}
