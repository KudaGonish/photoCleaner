package ru.kudagonish.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,              // Кнопки инвертируются в белый
    onPrimary = DeepAnthracite,
    primaryContainer = Color(0xFF334155),
    onPrimaryContainer = Color.White,

    secondary = BubbleBlue,             // Акценты сохраняем пастельными
    onSecondary = DeepAnthracite,
    secondaryContainer = Color(0xFF334155),
    onSecondaryContainer = BubbleBlue,

    background = DarkBg,
    onBackground = Color.White,

    surface = DarkSurface,
    onSurface = Color.White,

    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = DarkOnSurfaceVariant,

    outline = Color(0xFF334155),
    error = RedInstant,
    onError = Color.White
)
/*  TODO нужно будет выбрать какую тему ставить, после того как закончу со статистикой
  darkColorScheme(
    primary = Color.White,              // Кнопки инвертируются в белый
    onPrimary = DeepAnthracite,
    primaryContainer = DarkSurfaceVariant,
    onPrimaryContainer = Color.White,

    secondary = BubbleBlue,             // Акценты сохраняем пастельными
    onSecondary = DeepAnthracite,
    secondaryContainer = DarkSurfaceVariant,

    background = DarkBg,
    onBackground = Color.White,

    surface = DarkSurface,
    onSurface = Color.White,

    surfaceVariant = DarkSurfaceVariant,
    // onSurfaceVariant здесь отвечает за второстепенный текст
    onSurfaceVariant = DarkOnSurfaceVariant,

    outline = DarkSurfaceVariant,
    error = RedInstant,
    onError = Color.White
)*/

private val LightColorScheme = lightColorScheme(
    primary = DeepAnthracite,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE2E8F0),
    onPrimaryContainer = DeepAnthracite,

    secondary = BubbleBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF1F5F9),
    onSecondaryContainer = SlateGray,

    tertiaryContainer = BubbleBlue.copy(alpha = 0.2f),

    background = SoftBackground,
    onBackground = DeepAnthracite,

    surface = Color.White,
    onSurface = DeepAnthracite,

    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = SurfaceGray,

    outline = Color(0xFFE2E8F0),
    error = RedInstant,
    onError = Color.White
)

@Composable
fun PhotoCleanerTheme(
    darkTheme: Boolean? = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme == true || isSystemInDarkTheme() -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}