package ru.kudagonish.feature_clearing.ui.tab.lazyStack.scope

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList

interface LazyStackScope {
    fun <T> items(
        items: ImmutableList<T>,
        key: ((item: T) -> Any)? = null,
        content: @Composable (Int) -> Unit
    )
}