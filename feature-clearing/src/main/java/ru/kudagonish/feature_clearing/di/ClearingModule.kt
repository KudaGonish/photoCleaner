package ru.kudagonish.feature_clearing.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.kudagonish.feature_clearing.domain.DeleteImageUseCase
import ru.kudagonish.feature_clearing.domain.GetImagesUseCase
import ru.kudagonish.feature_clearing.domain.KeepImageUseCase
import ru.kudagonish.feature_clearing.domain.PhotosOperationRequestInteractor
import ru.kudagonish.feature_clearing.ui.tab.viewModel.ClearingTabViewModel

val clearingModule = module {
    factoryOf(::GetImagesUseCase)
    factoryOf(::KeepImageUseCase)
    factoryOf(::PhotosOperationRequestInteractor)
    factoryOf(::DeleteImageUseCase)
    viewModelOf(::ClearingTabViewModel)
}