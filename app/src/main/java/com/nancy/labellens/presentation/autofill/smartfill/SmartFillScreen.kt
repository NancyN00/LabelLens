package com.nancy.labellens.presentation.autofill.smartfill


import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.nancy.labellens.presentation.autofill.livecamerafill.LiveOcrCamera

@Composable
fun SmartFillScreen() {

    val context = LocalContext.current

    // Autofill states
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var showLiveCamera by remember { mutableStateOf(false) }


    // ML Kit OCR client
    val recognizer = remember {
        TextRecognition.getClient(
            TextRecognizerOptions.DEFAULT_OPTIONS
        )
    }

    // Gallery picker
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            processImage(context, it, recognizer) { result ->
                title = result.title
                date = result.date
                location = result.location
            }
        }
    }

    // Camera capture
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val image = InputImage.fromBitmap(it, 0)

            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val result = parseSmartFillText(visionText.text)
                    title = result.title
                    date = result.date
                    location = result.location
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("SmartFill", style = MaterialTheme.typography.headlineMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("Gallery")
            }

            Button(onClick = { cameraLauncher.launch(null) }) {
                Text("Take Picture")
            }

            Button(onClick = { showLiveCamera = true }) {
                Text("Live")
            }

        }

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title / Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location / Description") },
            modifier = Modifier.fillMaxWidth()
        )
    }
    if (showLiveCamera) {
        LiveOcrCamera(
            recognizer = recognizer,
            onResult = { result ->
                title = result.title
                date = result.date
                location = result.location
            },
            onClose = { showLiveCamera = false }
        )
    }



    }

private fun processImage(
    context: Context,
    uri: Uri,
    recognizer: TextRecognizer,
    onResult: (SmartFillResult) -> Unit
) {
    runOCR(context, uri, recognizer) { extractedText ->
        onResult(parseSmartFillText(extractedText))
    }
}


