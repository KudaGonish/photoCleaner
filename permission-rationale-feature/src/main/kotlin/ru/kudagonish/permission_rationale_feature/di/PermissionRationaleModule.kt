package ru.kudagonish.permission_rationale_feature.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kudagonish.permission_rationale_feature.data.PermissionRepositoryImpl
import ru.kudagonish.permission_rationale_feature.domain.PermissionRepository
import ru.kudagonish.permission_rationale_feature.ui.screens.PermissionsViewModel

val permissionRationaleModule = module {
    factoryOf(::PermissionRepositoryImpl) bind PermissionRepository::class
    viewModelOf(::PermissionsViewModel)
}
