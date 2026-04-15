package ru.kudagonish.feature_clearing.ui.tab.lazyStack.scope

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList

inline fun <T> LazyStackScope.items(
    items: ImmutableList<T>,
    noinline key: ((item: T) -> Any)? = null,
    crossinline content: @Composable (index: Int, T) -> Unit
) {
    items(items, key = key) { index -> content(index, items[index]) }
}