package com.nancy.labellens.presentation.autofill.smartfill

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer

fun runOCR(
    context: Context,
    imageUri: Uri,
    recognizer: TextRecognizer,
    onResult: (String) -> Unit
) {
    val image = InputImage.fromFilePath(context, imageUri)

    recognizer.process(image)
        .addOnSuccessListener { visionText ->
            onResult(visionText.text)
        }
        .addOnFailureListener {
            it.printStackTrace()
        }
}
