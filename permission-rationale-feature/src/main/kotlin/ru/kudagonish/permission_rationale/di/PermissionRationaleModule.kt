package ru.kudagonish.permission_rationale.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kudagonish.permission_rationale.data.PermissionRepositoryImpl
import ru.kudagonish.permission_rationale.domain.PermissionRepository
import ru.kudagonish.permission_rationale.screens.permissions.PermissionRationaleViewModel

val permissionRationaleModule = module {
    factoryOf(::PermissionRepositoryImpl) bind PermissionRepository::class
    viewModelOf(::PermissionRationaleViewModel)
}
