package com.nancy.labellens.presentation.autofill.smartfill.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nancy.labellens.domain.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmartFillViewModel @Inject constructor(
    private val repository: HistoryRepository // Injected via Hilt
) : ViewModel() {

    fun saveRecognizedText(title: String, date: String, location: String) {
        // Skip saving if nothing was found
        if (title.isBlank() && date.isBlank() && location.isBlank()) return

        val resultText = buildString {
            if (title.isNotBlank()) append("Title: $title\n")
            if (date.isNotBlank()) append("Date: $date\n")
            if (location.isNotBlank()) append("Location: $location")
        }.trim()

        viewModelScope.launch {
            repository.saveScan(type = "Text", result = resultText)
        }
    }
}
