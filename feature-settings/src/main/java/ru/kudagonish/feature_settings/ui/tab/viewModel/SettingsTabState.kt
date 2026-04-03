package ru.kudagonish.feature_settings.ui.tab.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.SettingsSuggest
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
    val isLoading: Boolean = true,
    val version: String = String(),
    val currentYear: String = TimeZone.currentSystemDefault().let {
        Clock.System.now().toLocalDateTime(it).year.toString()
    },
    val languages: ImmutableList<SelectionItem<Language>> = persistentListOf(),
    val themes: ImmutableList<SelectionItem<AppTheme>> = persistentListOf(),
    val algorithms: ImmutableList<SelectionItem<WorkAlgorithm>> = persistentListOf(),
    val deletionTypes: ImmutableList<SelectionItem<DeletionType>> = persistentListOf()
)

internal fun AppTheme.mapThemes() = defaultSettings.themes.map {
    SelectionItem(
        setting = it,
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
        setting = it,
        isSelected = it == this,
        title = when (it) {
            Language.Ru -> R.string.selection_ui_lang_ru
            Language.Eng -> R.string.selection_ui_lang_en
        }
    )
}.toImmutableList()

internal fun WorkAlgorithm.mapAlgorithms() = defaultSettings.algorithms.map {
    SelectionItem(
        setting = it,
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
    val settingIsDeffered = it is DeletionType.Deffered && this is DeletionType.Deffered

    SelectionItem(
        setting = if (settingIsDeffered) this else it,
        isSelected = when {
            settingIsDeffered -> true
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
        }
    )
}.toImmutableList()
