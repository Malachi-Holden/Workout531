package com.holden.workout531.plates

import arrow.optics.optics
import kotlin.math.abs

@optics
data class PlateSet(val bar: Double?, val plates: List<Double>) {
    // plates should be sorted largest to smallest

    /**
     * Uses a substandard but fast algorithm to find a set of plates that adds up to
     * something near the goal.
     */
    fun calculatePlates(goal: Double): CalcResult { // doesn't work if goal is less than bar
        bar?.let {
            if (goal < it){
                return CalcResult(null, null, listOf(), it - goal, false)
            }
        }

        val currentPlates: MutableList<Double> = mutableListOf()
        var total = bar ?: 0.0
        var lowError = abs(goal - total)
        val unusedPlates = plates.map { true }.toMutableList()
        for (plateIndex in plates.indices){
            val plate = plates[plateIndex]
            if (total + 2 * plate <= goal){
                lowError = abs(total + 2 * plate - goal)
                total += (2 * plate)
                currentPlates.add(plate)
                unusedPlates[plateIndex] = false
            }
        }
        val lastPlate = plates.withIndex().lastOrNull { unusedPlates[it.index] }?.value
        val (highPlates, highError) = if (lastPlate != null) {
            (currentPlates + lastPlate).sortedDescending() to total + 2 * lastPlate - goal
        } else {
            null to null
        }

        return CalcResult(currentPlates, lowError, highPlates, highError, lowError == 0.0)
    }

    companion object
}

data class CalcResult(val lowSet: List<Double>?, val lowError: Double?, val highSet: List<Double>?, val highError: Double?, val exact: Boolean = false)

