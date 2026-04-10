package com.nancy.labellens.presentation.history


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nancy.labellens.domain.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: HistoryRepository
) : ViewModel() {

    val historyFlow = repository.getHistory()

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearAll()
        }
    }
}
