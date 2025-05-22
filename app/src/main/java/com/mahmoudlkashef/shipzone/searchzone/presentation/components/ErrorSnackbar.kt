package com.mahmoudlkashef.shipzone.searchzone.presentation.components

import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorSnackbar(message: String) {
    Snackbar(action = {}) {
        Text(message)
    }
} 