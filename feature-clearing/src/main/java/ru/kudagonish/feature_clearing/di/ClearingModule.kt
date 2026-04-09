package ru.kudagonish.feature_clearing.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.kudagonish.feature_clearing.domain.GetImagesUseCase
import ru.kudagonish.feature_clearing.ui.tab.ClearingTabViewModel

val clearingModule = module {
    factoryOf(::GetImagesUseCase)
    viewModelOf(::ClearingTabViewModel)
}