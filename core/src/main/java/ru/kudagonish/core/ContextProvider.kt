package ru.kudagonish.core

import android.content.Context

interface ContextProvider {
    val context: Context
}

internal class ContextProviderImpl(override val context: Context) : ContextProvider