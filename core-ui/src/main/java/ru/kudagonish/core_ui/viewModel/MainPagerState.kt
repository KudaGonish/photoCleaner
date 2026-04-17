package ru.kudagonish.core_ui.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Stable
class MainPagerState {
    private val _targetTrashTab = MutableSharedFlow<Int>(extraBufferCapacity = 1)
    val targetTrashTab = _targetTrashTab.asSharedFlow()

    fun navigateToTrashDeletion() {
        _targetTrashTab.tryEmit(1)
    }
}

@Composable
fun rememberMainPagerState() = remember { MainPagerState() }