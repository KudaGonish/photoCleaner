package ru.kudagonish.feature_trash_bin.ui.tab.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.kudagonish.core_ui.elements.shadow

@Composable
internal fun ImageItem(
    modifier: Modifier,
    src: String,
) {
    var isLoaded by remember { mutableStateOf(false) }
    var contentScale by remember { mutableStateOf(ContentScale.Fit) }

    AsyncImage(
        model = src,
        contentDescription = null,
        onSuccess = { painterState ->
            isLoaded = true
            val size = painterState.painter.intrinsicSize
            if (size.width > 0 && size.height > 0) {
                val ratio = size.width / size.height
                contentScale =
                    if (ratio > 1.0f) ContentScale.Fit else ContentScale.FillWidth
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (!isLoaded) Modifier
                else modifier.shadow()
            )
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background),
        contentScale = contentScale,
    )
}