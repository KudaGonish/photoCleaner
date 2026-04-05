package ru.kudagonish.photocleaner.ui.activity

import android.content.res.Configuration
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@Composable
fun LocaleWrapper(
    language: String?,
    content: @Composable () -> Unit
) {
    if (language.isNullOrEmpty()) {
        content()
        return
    }

    val context = LocalContext.current
    val activity = LocalActivity.current
    val registryOwner = LocalActivityResultRegistryOwner.current
    val configuration = LocalConfiguration.current
    val localizedContext = remember(language, context) {
        val locale = Locale.forLanguageTag(language)
        Locale.setDefault(locale)

        val config = Configuration(configuration).apply {
            setLocale(locale)
        }
        context.createConfigurationContext(config)
    }

    CompositionLocalProvider(
        LocalContext provides localizedContext,
        LocalConfiguration provides localizedContext.resources.configuration,
        LocalActivity provides activity,
        LocalActivityResultRegistryOwner provides registryOwner!!,
    ) {
        content()
    }
}