package ru.kudagonish.permission_rationale_feature.domain

import kotlinx.coroutines.flow.Flow

interface PermissionRepository {
    fun getRequestPermissionCount(): Flow<Int>
    suspend fun updateRequestCount(count: Int)
    suspend fun resetRequestCount()
}
