package ru.kudagonish.feature_clearing.ui.tab.lazyStack

import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import ru.kudagonish.feature_clearing.ui.tab.lazyStack.scope.CustomLazyListScopeImpl
import ru.kudagonish.feature_clearing.ui.tab.lazyStack.scope.CustomLazyListScopeImpl.LazyItem
import ru.kudagonish.feature_clearing.ui.tab.lazyStack.scope.LazyStackScope

internal class StackItemProviderImpl(
    private val items: List<LazyItem>
) : LazyLayoutItemProvider {

    private val keyToIndexMap: Map<Any, Int> = items.withIndex().associateBy({ it.value.key }, { it.index })

    override val itemCount: Int
        get() = items.size

    @Composable
    override fun Item(index: Int, key: Any) {
        items.getOrNull(index)?.item?.invoke(index)
    }

    override fun getKey(index: Int): Any = items.getOrNull(index)?.key ?: "empty_$index"

    override fun getIndex(key: Any): Int = keyToIndexMap[key] ?: -1
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