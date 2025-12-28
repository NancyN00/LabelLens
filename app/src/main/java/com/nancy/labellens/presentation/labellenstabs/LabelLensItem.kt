package com.nancy.labellens.presentation.labellenstabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.outlined.AutoFixHigh
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.ui.graphics.vector.ImageVector

data class LabelLensItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

val tabItems = listOf(
    LabelLensItem(
        title = "Camera",
        unselectedIcon = Icons.Outlined.PhotoCamera,
        selectedIcon = Icons.Filled.PhotoCamera
    ),
    LabelLensItem(
        title = "History",
        unselectedIcon = Icons.Outlined.History,
        selectedIcon = Icons.Filled.History
    ),
    LabelLensItem(
        title = "SmartFill",
        unselectedIcon = Icons.Outlined.AutoFixHigh,
        selectedIcon = Icons.Filled.AutoFixHigh
    )
)

