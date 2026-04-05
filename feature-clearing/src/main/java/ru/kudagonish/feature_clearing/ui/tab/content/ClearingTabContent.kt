package ru.kudagonish.feature_clearing.ui.tab.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.kudagonish.core_ui.elements.containers.SwipeableCard

@Composable
internal fun ClearingTabContent() {
    val items = remember {
        mutableStateListOf(
            "Фото 1" to Color.Red,
            "Фото 2" to Color.Blue,
            "Фото 3" to Color.Green,
            "Фото 4" to Color.Yellow,
            "Фото 5" to Color.Magenta
        )
    }

    Box(
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
                        .size(300.dp, 450.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = item.second),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.first,
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
    }
}
