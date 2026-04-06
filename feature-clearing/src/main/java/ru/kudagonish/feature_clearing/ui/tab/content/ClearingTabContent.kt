package ru.kudagonish.feature_clearing.ui.tab.content

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

data class Item(
    val name: String,
    val background: Color,
    val size: DpSize
)

@Composable
internal fun ClearingTabContent() {
    val items = remember {
        mutableStateListOf(
            Item(
                name = "Фото 1",
                background = Color.Red,
                size = DpSize(400.dp, 300.dp)
            ),
            Item(
                name = "Фото 2",
                background = Color.Blue,
                size = DpSize(300.dp, 450.dp)
            ),
            Item(
                name = "Фото 3",
                background = Color.Green,
                size = DpSize(400.dp, 300.dp)
            ),
            Item(
                name = "Фото 4",
                background = Color.Yellow,
                size = DpSize(300.dp, 450.dp)
            ),
            Item(
                name = "Фото 5",
                background = Color.Magenta,
                size = DpSize(300.dp, 450.dp)
            )
        )
    }

    LazyColumn() {
        item{
            Box(Modifier.animateItem())
        }
    }
    Layout(
        measurePolicy = rememberMeasurePolicy(),
        content = {
            items.asReversed().forEach {
                Box(
                    Modifier
                        .size(it.size)
                        .clickable { items.remove(it) }
                        .background(it.background.copy(alpha = 0.2f))
                )
            }
        }
    )

    /*Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        // Отрисовываем карточки в обратном порядке, чтобы верхняя была последней в списке
        items.asReversed().forEach { item ->
            SwipeableCard(
                onSwiped = { direction ->
                    println("Swiped $direction")
                    items.remove(item)
                }
            ) {
                Card(
                    modifier = Modifier
                        .size(item.size)
                        .padding(16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = item.background),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }

        if (items.isEmpty()) {
            Text(
                text = "Все фото разобраны!",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }*/
}

@Composable
fun rememberMeasurePolicy(): MeasurePolicy = remember {
    MeasurePolicy { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }

        val centerWidth = constraints.maxWidth / 2
        val centerHeight = constraints.maxHeight / 2
        Log.d("TAG", "rememberMeasurePolicy: ")
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEachIndexed { index, placeable ->
                // Располагаем в центре
                val x = centerWidth - placeable.width / 2
                val y = centerHeight - placeable.height / 2

                placeable.placeWithLayer(x = x, y = y) {
                    val scale = if(index == 0) 1f else 0f
                    scaleX = scale
                    scaleY = scale

                    shape = RoundedCornerShape(16.dp)
                    clip = true
                }
            }
        }
    }
}
