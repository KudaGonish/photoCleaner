package ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.scope

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList
import ru.kudagonish.feature_clearing.ui.tab.content.Item

class CustomLazyListScopeImpl : LazyStackScope {
    private val _items = mutableListOf<LazyItem>()
    val items: List<LazyItem> = _items

    override fun items(
        items: ImmutableList<Item>,
        key: ((item: Item) -> Any)?,
        content: @Composable (index: Int) -> Unit
    ) {
        items.forEach { item ->
            val itemKey = key?.invoke(item) ?: item.id
            _items.add(LazyItem(itemKey, content))
        }
    }

    data class LazyItem(
        val key: Any,
        val item: @Composable (index: Int) -> Unit
    )
}