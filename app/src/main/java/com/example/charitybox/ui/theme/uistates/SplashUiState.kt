package com.example.charitybox.ui.theme.uistates

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SplashUiState(
    val startAnimation: Boolean = false,
    var hasNavigated: Boolean = false,
    val logoOffset: Dp = 0.dp
)
