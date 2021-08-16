package com.egoriku.radiotok.presentation.screen.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.egoriku.radiotok.R
import com.egoriku.radiotok.presentation.screen.settings.ui.SettingItem
import com.egoriku.radiotok.presentation.ui.header.ScreenHeader
import com.egoriku.radiotok.presentation.ui.header.SectionHeader
import com.google.accompanist.insets.systemBarsPadding
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        val settingsViewModel = getViewModel<SettingsViewModel>()

        val createBackupFileLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.CreateDocument()
        ) {
            settingsViewModel.createBackup(it)
        }

        val openBackup = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) {
            settingsViewModel.restoreBackup(it)
        }

        ScreenHeader(title = stringResource(R.string.settings_screen_header))

        LazyColumn {
            item {
                SectionHeader(title = stringResource(R.string.settings_section_backup_header)) {
                    SettingItem(
                        title = stringResource(R.string.settings_section_backup_title),
                        subtitle = stringResource(R.string.settings_section_backup_description),
                    ) {
                        createBackupFileLauncher.launch(settingsViewModel.backupFileName)
                    }
                    SettingItem(
                        title = stringResource(R.string.settings_section_restore_title),
                        subtitle = stringResource(R.string.settings_section_restore_description)
                    ) {
                        openBackup.launch(("*/*"))
                    }
                }
            }
        }
    }
}