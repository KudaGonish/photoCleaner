package ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.scope

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList
import ru.kudagonish.feature_clearing.ui.tab.content.Item

class CustomLazyListScopeImpl : LazyStackScope {
    private val _items = mutableListOf<LazyItem>()
    val items: List<LazyItem> = _items

    override fun items(
        items: ImmutableList<Item>,
//        key: ((item: Item) -> Any)?,
        content: @Composable (index: Int) -> Unit
    ) {
        items.forEachIndexed { index, item ->
            _items.add(LazyItem(index, item.name, content))
        }
    }

    data class LazyItem(
        val index: Int,
        val key: String,
        val item: @Composable (index: Int) -> Unit
    )
}