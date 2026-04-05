package ru.kudagonish.photocleaner.ui.activity

import android.app.LocaleManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.android.ext.android.inject
import ru.kudagonish.core_ui.theme.SoftBackground
import ru.kudagonish.photocleaner.ui.activity.viewModel.MainViewModel
import ru.kudagonish.photocleaner.ui.activity.viewModel.MainViewModel.Event
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        viewModel.sendEvent(Event.OnSetApplicationLanguage(getSystemLocale()))
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(SoftBackground.toArgb(), SoftBackground.toArgb()),
            navigationBarStyle = SystemBarStyle.light(
                SoftBackground.toArgb(),
                SoftBackground.toArgb()
            )
        )
        splashScreen.setKeepOnScreenCondition { viewModel.requestPermissionCount.value == null }
        setApplicationContent(viewModel)
    }

    private fun getSystemLocale(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSystemService(LocaleManager::class.java).systemLocales.get(0)
        } else {
            Locale.getDefault()
        }.language
    }
}