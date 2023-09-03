package com.holden.workout531

import androidx.compose.runtime.compositionLocalOf
import com.holden.workout531.preferences.Preferences531
import kotlinx.serialization.Serializable

@Serializable
data class Units(val weightUnit: String)

val LocalUnits = compositionLocalOf { Units(weightUnit = "lbs") }
val LocalPlateSet = compositionLocalOf { Preferences531.defaultPlates }