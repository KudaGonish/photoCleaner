package ru.kudagonish.feature_clearing.ui.tab.lazyStack.scope

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList

internal class CustomLazyListScopeImpl : LazyStackScope {
    private val _items = mutableListOf<LazyItem>()
    val items: List<LazyItem> = _items

    override fun <T> items(
        items: ImmutableList<T>,
        key: ((item: T) -> Any)?,
        content: @Composable (index: Int) -> Unit
    ) {
        items.forEachIndexed { index, item ->
            val itemKey = key?.invoke(item) ?: "empty_$index"
            _items.add(LazyItem(itemKey, content))
        }
    }

    data class LazyItem(
        val key: Any,
        val item: @Composable (index: Int) -> Unit
    )
}