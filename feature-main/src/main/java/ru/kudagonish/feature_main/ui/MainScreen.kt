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
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.RestoreFromTrash
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen() {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 1, pageCount = { 3 })

    val pages = listOf(
        PagerItem(
            icon = Icons.Outlined.RestoreFromTrash,
            title = "Корзина",
            onClick = {scope.launch { pagerState.scrollToPage(0) }},
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Корзина", color = Color.Black)
                }
            }
        ),
        PagerItem(
            icon = Icons.Outlined.Image,
            title = "Отчистка",
            onClick = { scope.launch { pagerState.scrollToPage(1) } },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Отчистка", color = Color.Black)
                }
            }
        ),
        PagerItem(
            icon = Icons.Outlined.Settings,
            title = "Настройки",
            onClick = { scope.launch { pagerState.scrollToPage(2) } },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Настройки", color = Color.Black)
                }
            }
        )
    )


    // Храним стабильную информацию о прыжке: 
    // (какой индекс физической страницы подменить -> какой реальный контент там показать)
    var jumpInfo by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false
        ) { pageIndex ->
            // Используем зафиксированный индекс для подмены
            val displayPageIndex = if (pageIndex == jumpInfo?.first) {
                jumpInfo!!.second
            } else {
                pageIndex
            }

            pages[pageIndex].content()
        }
        BottomMenu(pagerState.currentPage, pages)

        /*        Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        scope.launch {
                            val targetPage = 0
                            val current = pagerState.currentPage

                            if (current > 1) {
                                val fakeIndex = current - 1
                                jumpInfo =
                                    fakeIndex to targetPage // Фиксируем: страница слева теперь выглядит как Первая
                                pagerState.animateScrollToPage(fakeIndex)
                                pagerState.scrollToPage(targetPage)
                                jumpInfo = null
                            } else if (current != targetPage) {
                                pagerState.animateScrollToPage(targetPage)
                            }
                        }
                    }) {
                        Text("На 1 страницу")
                    }

                    Button(onClick = {
                        scope.launch {
                            val targetPage = pageCount - 1
                            val current = pagerState.currentPage

                            if (current < targetPage - 1) {
                                val fakeIndex = current + 1
                                jumpInfo =
                                    fakeIndex to targetPage // Фиксируем: страница справа теперь выглядит как Последняя
                                pagerState.animateScrollToPage(fakeIndex)
                                pagerState.scrollToPage(targetPage)
                                jumpInfo = null
                            } else if (current != targetPage) {
                                pagerState.animateScrollToPage(targetPage)
                            }
                        }
                    }) {
                        Text("На последнюю")
                    }
                }*/
    }
}

@Composable
internal fun BoxScope.BottomMenu(
    currentPage: Int,
    items: List<PagerItem>
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
                            onClick = item.onClick
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

internal data class PagerItem(
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit,
    val content: @Composable () -> Unit
)

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}
