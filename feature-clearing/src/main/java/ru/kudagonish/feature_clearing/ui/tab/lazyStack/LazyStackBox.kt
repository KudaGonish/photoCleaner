package ru.kudagonish.feature_clearing.ui.tab.lazyStack

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.kudagonish.feature_clearing.ui.tab.lazyStack.scope.LazyStackScope


@Composable
internal fun LazyStackBox(
    modifier: Modifier = Modifier,
    stackState: LazyStackState,
    content: LazyStackScope.() -> Unit
) {
    val itemProvider = rememberStackItemProviderLambda(content, stackState)
    val measurePolicy = rememberStackMeasurePolicy(stackState, itemProvider)
    LazyLayout(
        modifier = modifier.fillMaxSize(),
        itemProvider = itemProvider,
        measurePolicy = measurePolicy,
    )
}