package com.egoriku.radiotok.presentation.screen.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egoriku.radiotok.util.BackupManager
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val backupManager: BackupManager
) : ViewModel() {

    val backupFileName: String
        get() = backupManager.backupFileName

    fun createBackup(uri: Uri) {
        viewModelScope.launch {
            backupManager.backup(outputFile = uri)
        }
    }

    fun restoreBackup(uri: Uri) {
        viewModelScope.launch {
            backupManager.restore(inputFile = uri)
        }
    }
}