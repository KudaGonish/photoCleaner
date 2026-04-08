package ru.kudagonish.feature_clearing.ui.tab.content.lazyStack

import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import ru.kudagonish.feature_clearing.ui.tab.content.LazyStackState
import ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.scope.CustomLazyListScopeImpl
import ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.scope.CustomLazyListScopeImpl.LazyItem
import ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.scope.LazyStackScope

class StackItemProviderImpl(private val items: List<LazyItem>) : LazyLayoutItemProvider {
    override val itemCount: Int
        get() = items.size

    @Composable
    override fun Item(index: Int, key: Any) {
        val item = items.getOrNull(index)
        item?.item?.invoke(index)
    }

    override fun getKey(index: Int): Any = items.getOrNull(index)?.key ?: "empty"
    override fun getIndex(key: Any): Int = items.indexOfFirst { it.key == key }
}

@Composable
internal fun rememberStackItemProviderLambda(
    content: LazyStackScope.() -> Unit,
    stackState: LazyStackState
): () -> LazyLayoutItemProvider {
    val latestContent = rememberUpdatedState(content)
    return remember(stackState) {
        val itemProvider = derivedStateOf(referentialEqualityPolicy()) {
            val scope = CustomLazyListScopeImpl().apply(latestContent.value)
            StackItemProviderImpl(scope.items)
        }
        itemProvider::value
    }
}