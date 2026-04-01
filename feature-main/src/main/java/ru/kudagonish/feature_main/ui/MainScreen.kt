package ru.kudagonish.feature_main.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DeleteSweep
import androidx.compose.material.icons.outlined.Http
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.RestoreFromTrash
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ViewCozy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.kudagonish.core_ui.elements.containers.pager.models.PagerItem
import ru.kudagonish.feature_main.R

@Composable
internal fun MainScreen() {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 1, pageCount = { 3 })
    var jumpInfo by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    val pages = listOf(
        PagerItem(
            icon = Icons.Outlined.DeleteSweep,
            title = stringResource(R.string.tab_trash),
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
            title = stringResource(R.string.tab_clean),
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
        PagerItem(
            icon = Icons.Outlined.Settings,
            title = stringResource(R.string.tab_settings),
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    //TODO таб из модуля с настройками
                }
            }
        )
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

@Composable
internal fun BoxScope.BottomMenu(
    currentPage: Int,
    items: List<PagerItem>,
    onClick: (targetPage: Int) -> Unit
) {
    Row(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .dropShadow(
                shape = CircleShape,
                shadow = Shadow(
                    radius = 6.dp,
                    color = Color.Black,
                    alpha = 0.2f
                )
            )
            .clip(CircleShape)
            .background(Color.White)
            .padding(vertical = 4.dp, horizontal = 6.dp)
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        items.forEachIndexed { index, item ->
            val backgroundColor by animateColorAsState(
                targetValue = if (currentPage == index) Color.Green.copy(alpha = 0.2f) else Color.White,
                label = ""
            )
            key(item.icon) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(32.dp))
                        .background(backgroundColor)
                        .padding(
                            vertical = 4.dp,
                            horizontal = 16.dp
                        )
                        .clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = { onClick(index) }
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier
                            .size(28.dp),
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Text(text = item.title)
                }
                if (items.lastIndex != index)
                    Spacer(Modifier.width(8.dp))
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}
