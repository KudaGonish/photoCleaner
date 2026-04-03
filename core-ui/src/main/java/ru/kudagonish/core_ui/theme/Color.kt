package ru.kudagonish.core_ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// --- ОСНОВНЫЕ ЦВЕТА ИЗ ТВОИХ ЭКРАНОВ ---
val DeepAnthracite = Color(0xFF111827)
val SlateGray = Color(0xFF4B5563)
val SurfaceGray = Color(0xFF7E8693)
val SoftBackground = Color(0xFFF8FAFC)

// --- ЦВЕТА ИЗ ГРАДИЕНТОВ (BUBBLES) ---
val BubbleBlue = Color(0xFF90CAF9)
val BubblePink = Color(0xFFF48FB1)
val BubblePurple = Color(0xFFB39DDB)
val BubbleYellow = Color(0xFFFFF59D)

// --- ЦВЕТА УТИЛИЗАЦИИ ---
val RedInstant = Color(0xFFEF4444)
val GreenSafe = Color(0xFF10B981)
val BlueBin = Color(0xFF3B82F6)

// Цвета для Dark Theme
val DarkBg = Color(0xFF0F172A)
val DarkSurface = Color(0xFF1E293B)
val DarkOnSurfaceVariant = Color(0xFF94A3B8)


//TODO нужно будет выбрать какую тему ставить, после того как закончу со статистикой
/*
val DarkBg = Color(0xFF121212)
val DarkSurface = Color(0xFF1E1E1E)
val DarkSurfaceVariant = Color(0xFF2C2C2C)
val DarkOnSurfaceVariant = Color(0xFF9CA3AF)
*/

/**
 * Кастомные цвета для специфических блоков
 */
data class CustomColors(
    val instant: Color = RedInstant,
    val safe: Color = GreenSafe,
    val bin: Color = BlueBin,
    val navActive: Color = BubbleBlue.copy(alpha = 0.3f),
    val bubbleGradients: List<Color> = listOf(BubbleBlue, BubblePink, BubblePurple, BubbleYellow)
)

val LocalCustomColors = staticCompositionLocalOf { CustomColors() }