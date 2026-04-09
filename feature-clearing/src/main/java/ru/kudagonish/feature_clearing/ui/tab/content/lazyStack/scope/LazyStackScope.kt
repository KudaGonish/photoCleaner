package ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.scope

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList
import ru.kudagonish.feature_clearing.ui.tab.content.Item

interface LazyStackScope {
    fun items(
        items: ImmutableList<Item>,
        key: ((item: Item) -> Any)? = null,
        content: @Composable (Int) -> Unit
    )
}