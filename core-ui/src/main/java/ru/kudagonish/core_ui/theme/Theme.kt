package ru.kudagonish.core_ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = DeepAnthracite,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE2E8F0),
    onPrimaryContainer = DeepAnthracite,

    // Вторичный акцент (Активные табы, выделенные значения в настройках)
    secondary = BubbleBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF1F5F9), // Неактивный фон табов/селекторов
    onSecondaryContainer = SlateGray,

    // Третичный акцент (Иконки в настройках — теперь используем нежно-голубой из градиента)
    tertiary = BubbleBlue,
    onTertiary = DeepAnthracite,
    tertiaryContainer = BubbleBlue.copy(alpha = 0.2f),
    onTertiaryContainer = DeepAnthracite,

    // Фон всего приложения
    background = SoftBackground,
    onBackground = DeepAnthracite,

    // Поверхности (Карточки, плашки настроек)
    surface = Color.White,
    onSurface = DeepAnthracite,

    // Второстепенный текст и разделители
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = SlateGray,

    outline = Color(0xFFE2E8F0),
    error = RedInstant,
    onError = Color.White
)

@Composable
fun PhotoCleanerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}