package com.mahmoudlkashef.shipzone.searchzone.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomLoadingProgress(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    indicatorColor: Color = Color.Black,
    trackColor: Color = Color.LightGray,
    indicatorSize: Dp = 42.dp,
    strokeWidth: Dp = 8.dp
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = indicatorColor,
                trackColor = trackColor,
                modifier = Modifier.size(indicatorSize),
                strokeWidth = strokeWidth
            )
        }
    }
}