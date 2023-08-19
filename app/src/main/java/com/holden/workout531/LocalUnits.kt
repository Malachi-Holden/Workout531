package com.holden.workout531

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

data class Units(val weightUnit: String)

val LocalUnits = compositionLocalOf { Units(weightUnit = "lbs") }