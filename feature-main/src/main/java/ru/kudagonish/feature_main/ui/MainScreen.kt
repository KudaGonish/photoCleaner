package ru.kudagonish.feature_main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import ru.kudagonish.core_ui.elements.BottomMenu
import ru.kudagonish.core_ui.viewModel.rememberMainPagerState
import ru.kudagonish.feature_clearing.ui.navigation.clearingTabItem
import ru.kudagonish.feature_settings.ui.navigation.settingsTabItem
import ru.kudagonish.feature_trash_bin.ui.navigation.trashBinTabItem

@Composable
internal fun MainScreen() {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 1, pageCount = { 3 })
    val mainPagerState = rememberMainPagerState()
    var jumpInfo by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    val pages = persistentListOf(
        trashBinTabItem(mainPagerState),
        clearingTabItem(
            onNavigateToBinDeletionTab = {
                scope.launch {
                    pagerState.animateScrollToPage(0)
                    mainPagerState.navigateToTrashDeletion()
                }
            }
        ),
        settingsTabItem()
    )

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false
        ) { pageIndex ->
            val displayPageIndex = if (pageIndex == jumpInfo?.first) {
                jumpInfo!!.second
            } else {
                pageIndex
            }

            pages[displayPageIndex].content()
        }
        BottomMenu(
            currentPage = jumpInfo?.second ?: pagerState.currentPage,
            items = pages,
            onClick = { targetPage ->
                scope.launch {
                    val current = pagerState.currentPage
                    val fakeIndex = if (targetPage < current) current - 1 else current + 1

                    jumpInfo = fakeIndex to targetPage
                    pagerState.animateScrollToPage(fakeIndex)
                    pagerState.scrollToPage(targetPage)
                    jumpInfo = null
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}