package ru.kudagonish.permission_rationale_feature.ui.screens.permissions.ui

import androidx.compose.animation.core.tween

internal fun <T> getTweenSpec(delay: Int) =
    tween<T>(ANIM_DURATION, delayMillis = delay)

private const val ANIM_DURATION = 600