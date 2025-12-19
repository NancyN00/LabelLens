package com.nancy.labellens.presentation.autofill

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions


@Composable
fun SmartFillScreen(){

    val context = LocalContext.current

    var title by remember {mutableStateOf("")}
    var date by remember {mutableStateOf((""))}
    var location by remember {mutableStateOf((""))}

    //ocr recognizer
    val recognizer = remember{
        TextRecognition.getClient(
            TextRecognizerOptions.DEFAULT_OPTIONS
        )
    }

    //gallery picker
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        uri : Uri? ->
            uri?.let {
                runOCR(context, it, recognizer){ extractedText ->
                    //val result = parse

                }
            }


    }

    OutlinedTextField(
        value = title,
        onValueChange = {title = it},
        label = { Text("Title / Name") },
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = location,
        onValueChange = {location = it},
        label = {Text("Location")},
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = date,
        onValueChange = {date = it},
        label = {Text("Date")},
        modifier = Modifier.fillMaxWidth()
    )

}
