package com.nancy.labellens.presentation.camera

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.nancy.labellens.domain.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repository: HistoryRepository
) : ViewModel() {

    private val _detectedLabels = MutableStateFlow<List<String>>(emptyList())
    val detectedLabels: StateFlow<List<String>> = _detectedLabels.asStateFlow()

    private val _showLabelsSheet = MutableStateFlow(false)
    val showLabelsSheet: StateFlow<Boolean> = _showLabelsSheet.asStateFlow()

    private val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

    fun onScanButtonClicked(bitmap: Bitmap?) {
        if (bitmap == null) {
            _detectedLabels.value = listOf("Error: Could not capture image frame")
            _showLabelsSheet.value = true
            return
        }
        val image = InputImage.fromBitmap(bitmap, 0)
        processImage(image)
    }

    fun processGalleryImage(context: Context, uri: Uri) {
        try {
            val image = InputImage.fromFilePath(context, uri)
            processImage(image)
        } catch (e: IOException) {
            _detectedLabels.value = listOf("Error loading image: ${e.message}")
            _showLabelsSheet.value = true
        }
    }

    private fun processImage(image: InputImage) {
        labeler.process(image)
            .addOnSuccessListener { labels ->
                // label results with confidence percentage
                val resultList = labels.map { label ->
                    "${label.text} (${(label.confidence * 100).toInt()}%)"
                }

                val resultsToDisplay = resultList.ifEmpty { listOf("No objects detected") }
                _detectedLabels.value = resultsToDisplay
                _showLabelsSheet.value = true

                // Convert list to comma-separated string and save to room database history
                val resultText = resultsToDisplay.joinToString(", ")
                viewModelScope.launch {
                    repository.saveScan(type = "Label", result = resultText)
                }
            }
            .addOnFailureListener { e ->
                _detectedLabels.value = listOf("Error: ${e.message}")
                _showLabelsSheet.value = true
            }
    }

    fun dismissLabelsSheet() {
        _showLabelsSheet.value = false
    }
}
