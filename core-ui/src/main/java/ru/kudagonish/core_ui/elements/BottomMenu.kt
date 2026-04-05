package ru.kudagonish.core_ui.elements

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import ru.kudagonish.core_ui.elements.containers.pager.models.PagerItem
import ru.kudagonish.core_ui.theme.LocalCustomColors

private val menuHeight = 61.dp
val bottomMenuPadding = menuHeight

@Composable
fun BoxScope.BottomMenu(
    currentPage: Int,
    items: ImmutableList<PagerItem>,
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
            .background(MaterialTheme.colorScheme.surface)
            .padding( horizontal = 4.dp)
            .height(menuHeight)
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        items.forEachIndexed { index, item ->
            val backgroundColor by animateColorAsState(
                targetValue = if (currentPage == index) LocalCustomColors.current.navActive
                else MaterialTheme.colorScheme.surface,
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
                        modifier = Modifier.size(28.dp),
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = item.title.asString(),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (items.lastIndex != index)
                    Spacer(Modifier.width(8.dp))
            }
        }
    }
}