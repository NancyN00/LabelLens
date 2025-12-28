package com.nancy.labellens.presentation.autofill.smartfill

import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun SmartFillPermissionWrapper(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    // Permissions
    val permissions = mutableListOf(
        android.Manifest.permission.CAMERA
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissions.add(android.Manifest.permission.READ_MEDIA_IMAGES)
    } else {
        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    val multiplePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsResult ->
        // Optional: Check results if needed
        permissionsResult.forEach { (perm, granted) ->
            if (!granted) {
                Toast.makeText(context, "Permission denied: $perm", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        multiplePermissionLauncher.launch(permissions.toTypedArray())
    }

    // Only show content if all permissions are granted
    val allGranted = permissions.all { perm ->
        ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED
    }

    if (allGranted) {
        content() // Your SmartFillScreen goes here
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Please grant camera and storage permissions to continue")
        }
    }
}
