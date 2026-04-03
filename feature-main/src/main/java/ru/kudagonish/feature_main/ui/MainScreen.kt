package ru.kudagonish.feature_main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteSweep
import androidx.compose.material.icons.outlined.ViewCozy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import ru.kudagonish.core_ui.elements.BottomMenu
import ru.kudagonish.core_ui.elements.containers.pager.models.PagerItem
import ru.kudagonish.feature_main.R
import ru.kudagonish.feature_settings.ui.navigation.settingsTabItem

@Composable
internal fun MainScreen() {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 1, pageCount = { 3 })
    var jumpInfo by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    val pages = persistentListOf(
        PagerItem(
            icon = Icons.Outlined.DeleteSweep,
            title = R.string.tab_trash,
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Green),
                    contentAlignment = Alignment.Center
                ) {
                    //TODO таб из модуля корзины, виден только если выбран режим "удалить через N дней"
                }
            }
        ),
        PagerItem(
            icon = Icons.Outlined.ViewCozy,
            title = R.string.tab_clean,
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red),
                    contentAlignment = Alignment.Center
                ) {
                    //TODO таб из модуля с отчисткой
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