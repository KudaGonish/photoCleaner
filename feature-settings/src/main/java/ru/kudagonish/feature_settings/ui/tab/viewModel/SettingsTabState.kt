package ru.kudagonish.feature_settings.ui.tab.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.SettingsSuggest
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.kudagonish.datastore.settings.DataStoreSettings.Companion.defaultSettings
import ru.kudagonish.datastore.settings.models.AppTheme
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.datastore.settings.models.Language
import ru.kudagonish.datastore.settings.models.WorkAlgorithm
import ru.kudagonish.feature_settings.R
import kotlin.time.Clock

internal data class SettingsTabState(
    val version: String = String(),
    val currentYear: String = TimeZone.currentSystemDefault().let {
        Clock.System.now().toLocalDateTime(it).year.toString()
    },
    val themes: ImmutableList<SelectionItem> = persistentListOf(),
    val languages: ImmutableList<SelectionItem> = persistentListOf(),
    val algorithms: ImmutableList<SelectionItem> = persistentListOf(),
    val deletionTypes: ImmutableList<SelectionItem> = persistentListOf()
)

internal fun AppTheme.mapThemes() = defaultSettings.themes.map {
    SelectionItem(
        isSelected = it == this,
        icon = when (it) {
            AppTheme.Light -> Icons.Outlined.LightMode
            AppTheme.Dark -> Icons.Outlined.DarkMode
            AppTheme.System -> Icons.Outlined.SettingsSuggest
        }
    )
}.toImmutableList()

internal fun Language.mapLanguages() = defaultSettings.languages.map {
    SelectionItem(
        isSelected = it == this,
        title = when (it) {
            Language.Ru -> R.string.selection_ui_lang_ru
            Language.Eng -> R.string.selection_ui_lang_en
        }
    )
}.toImmutableList()

internal fun WorkAlgorithm.mapAlgorithms() = defaultSettings.algorithms.map {
    SelectionItem(
        isSelected = it == this,
        title = when (it) {
            WorkAlgorithm.DayMoth -> R.string.selection_algo_day_month
            WorkAlgorithm.FullTime -> R.string.selection_algo_full_time
        },
        description = when (it) {
            WorkAlgorithm.DayMoth -> R.string.selection_algo_day_month_desc
            WorkAlgorithm.FullTime -> R.string.selection_algo_full_dec
        }
    )
}.toImmutableList()

internal fun DeletionType.mapDeletionTypes() = defaultSettings.deletionTypes.map {
    SelectionItem(
        isSelected = when {
            it is DeletionType.Deffered && this is DeletionType.Deffered -> true
            else -> it == this
        },
        title = when (it) {
            DeletionType.Instant -> R.string.selection_util_instantly
            is DeletionType.Deffered -> R.string.selection_util_deffered
            DeletionType.SystemTrash -> R.string.selection_util_system_basket
        },
        description = when (it) {
            DeletionType.Instant -> R.string.selection_util_instantly_desc
            is DeletionType.Deffered -> R.string.selection_util_deffered_desc
            DeletionType.SystemTrash -> R.string.selection_util_system_basket_desc
        },
        icon = when (it) {
            DeletionType.Instant -> Icons.Outlined.Bolt
            is DeletionType.Deffered -> Icons.Outlined.Schedule
            DeletionType.SystemTrash -> Icons.Outlined.Delete
        },
        color = when (it) {
            DeletionType.Instant -> Color.Red
            is DeletionType.Deffered -> Color.Green
            DeletionType.SystemTrash -> Color.Blue
        }
    )
}.toImmutableList()
