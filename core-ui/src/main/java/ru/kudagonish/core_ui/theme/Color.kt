package ru.kudagonish.core_ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

// --- ОСНОВНЫЕ ЦВЕТА ИЗ ТВОИХ ЭКРАНОВ ---
val DeepAnthracite = Color(0xFF111827) // Твой основной черный (кнопки, заголовки)
val SlateGray = Color(0xFF4B5563)      // Твой серый (описания, второстепенный текст)
val SoftBackground = Color(0xFFF8FAFC) // Рекомендованный фон приложения (Slate 50)

// --- ЦВЕТА ИЗ ГРАДИЕНТОВ (BUBBLES) ---
val BubbleBlue = Color(0xFF90CAF9)
val BubblePink = Color(0xFFF48FB1)
val BubblePurple = Color(0xFFB39DDB)
val BubbleYellow = Color(0xFFFFF59D)

// --- АКЦЕНТЫ ДЛЯ НАСТРОЕК И ТАБОВ ---
val AccentIndigo = Color(0xFF4F46E5)      // Для активных табов и синих карточек

// --- ЦВЕТА УТИЛИЗАЦИИ ---
val RedInstant = Color(0xFFEF4444)     // Молния (Мгновенно)
val GreenSafe = Color(0xFF10B981)      // Часики (Безопасно)
val BlueBin = Color(0xFF3B82F6)        // Корзина

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