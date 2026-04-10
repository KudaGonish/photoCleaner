package ru.kudagonish.feature_clearing.ui.tab.lazyStack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import kotlin.math.abs

@Stable
internal class LazyStackState {
    private var _offset = mutableIntStateOf(0)

    val itemScaling: Float
        get() = (abs(_offset.intValue) / 900f).coerceIn(0f, 1f)

    val itemAlpha: Float
        get() = (abs(_offset.intValue) / 600f).coerceIn(0f, 1f)

    fun updateTopItemOffset(value: Int) {
        _offset.intValue = value
    }
}

@Composable
internal fun rememberLazyStackState() = remember { LazyStackState() }