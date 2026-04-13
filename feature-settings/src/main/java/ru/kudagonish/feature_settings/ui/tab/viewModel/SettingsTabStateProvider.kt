package ru.kudagonish.feature_settings.ui.tab.viewModel

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.kudagonish.datastore.settings.models.AppTheme
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.datastore.settings.models.Language
import ru.kudagonish.datastore.settings.models.WorkAlgorithm

internal class SettingsTabStateProvider : PreviewParameterProvider<SettingsTabState> {
    override val values: Sequence<SettingsTabState> = sequenceOf(
        // Состояние загрузки
        SettingsTabState(isLoading = true),
        
        // Стандартное состояние (Светлая тема, Русский язык)
        SettingsTabState(
            isLoading = false,
            version = "1.0.0",
            languages = Language.Ru.mapLanguages(),
            themes = AppTheme.Light.mapThemes(),
            algorithms = WorkAlgorithm.DayMoth.mapAlgorithms(),
            deletionTypes = DeletionType.Instant.mapDeletionTypes()
        ),

        // Альтернативное состояние (Темная тема, Английский, Мгновенное удаление)
        SettingsTabState(
            isLoading = false,
            version = "1.0.0",
            languages = Language.Eng.mapLanguages(),
            themes = AppTheme.Dark.mapThemes(),
            algorithms = WorkAlgorithm.FullTime.mapAlgorithms(),
            deletionTypes = DeletionType.Instant.mapDeletionTypes()
        )
    )
}
