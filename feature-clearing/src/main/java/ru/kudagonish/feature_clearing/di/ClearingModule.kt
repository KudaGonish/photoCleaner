package ru.kudagonish.feature_clearing.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.kudagonish.feature_clearing.domain.ClearingInformationInteractor
import ru.kudagonish.feature_clearing.domain.PhotoActionInteractor
import ru.kudagonish.feature_clearing.domain.PhotoKeepActionUseCase
import ru.kudagonish.feature_clearing.domain.PhotoNegativeActionUseCase
import ru.kudagonish.feature_clearing.domain.PhotosOperationRequestInteractor
import ru.kudagonish.feature_clearing.ui.tab.viewModel.ClearingTabViewModel

val clearingModule = module {
    factoryOf(::PhotoKeepActionUseCase)
    factoryOf(::PhotosOperationRequestInteractor)
    factoryOf(::PhotoNegativeActionUseCase)
    factoryOf(::ClearingInformationInteractor)
    factoryOf(::PhotoActionInteractor)
    viewModelOf(::ClearingTabViewModel)
}