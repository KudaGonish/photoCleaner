package ru.kudagonish.feature_clearing.ui.tab.lazyStack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import ru.kudagonish.core_ui.elements.containers.SwipeDirection
import kotlin.math.abs

@Stable
internal class LazyStackState {
    private var _offset = mutableIntStateOf(0)
    private var _swipeDirection: MutableState<SwipeDirection?> = mutableStateOf(null)

    val itemScaling: Float
        get() = (abs(_offset.intValue) / 900f).coerceIn(0f, 1f)

    val itemAlpha: Float
        get() = (abs(_offset.intValue) / 600f).coerceIn(0f, 1f)

    val swipeDirection: SwipeDirection?
        get() = _swipeDirection.value

    fun updateTopItemOffset(value: Int) {
        _offset.intValue = value
    }

    fun updateSwipeDirection(value: SwipeDirection?) {
        _swipeDirection.value = value
    }
}

@Composable
internal fun rememberLazyStackState() = remember { LazyStackState() }