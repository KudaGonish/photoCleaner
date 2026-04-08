package ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.scope

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList
import ru.kudagonish.feature_clearing.ui.tab.content.Item

inline fun LazyStackScope.items(
    items: ImmutableList<Item>,
    crossinline content: @Composable (index: Int, Item) -> Unit
) {
    items(items) { index -> content(index, items[index]) }
}