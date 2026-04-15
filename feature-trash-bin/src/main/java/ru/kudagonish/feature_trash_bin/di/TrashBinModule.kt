package ru.kudagonish.feature_trash_bin.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel

val trashBinModule = module {
    viewModelOf(::TrashBinViewModel)
}