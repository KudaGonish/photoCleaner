package ru.kudagonish.feature_settings.ui.tab.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.kudagonish.core_ui.elements.shimmer

@Composable
internal fun SettingsTabShimmer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 18.dp)
                .size(width = 120.dp, height = 32.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmer()
                .align(Alignment.Start)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(28.dp))
                .shimmer()
        )
        repeat(3) {
            ShimmerSection()
        }
    }
}

@Composable
private fun ShimmerSection() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(16.dp).clip(RoundedCornerShape(4.dp)).shimmer())
            Spacer(Modifier.width(8.dp))
            Box(Modifier.size(width = 100.dp, height = 14.dp).clip(RoundedCornerShape(4.dp)).shimmer())
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(28.dp))
                .shimmer()
        )
    }
}
