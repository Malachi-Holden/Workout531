package com.holden.workout531.utility

import kotlin.math.pow
import kotlin.math.round

fun Double.roundTo(places: Int) = 10.0.pow(places).let { round(this * it) / it }