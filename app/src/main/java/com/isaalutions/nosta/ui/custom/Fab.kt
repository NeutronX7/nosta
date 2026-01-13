package com.isaalutions.nosta.ui.custom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun Fab(
    onClick: () -> Unit
) {
    FloatingActionButton(onClick) {
        Icon(Icons.Outlined.Add, "Floating action button.")
    }
}