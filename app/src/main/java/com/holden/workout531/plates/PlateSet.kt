package com.holden.workout531.plates

import arrow.optics.optics
import kotlinx.serialization.Serializable
import kotlin.math.abs

@Serializable
@optics
data class PlateSet(val bar: Double?, val plates: List<Double>) {
    // plates should be sorted largest to smallest

    fun calculateUnderEstimate(goal: Double): CalcResult? {
        if (bar != null){
            if (goal < bar){
                return null
            }
        }

        val currentPlates: MutableList<Double> = mutableListOf()
        var total = bar ?: 0.0
        var lowError = abs(goal - total)
        for (plateIndex in plates.indices){
            val plate = plates[plateIndex]
            if (total + 2 * plate <= goal){
                lowError = abs(total + 2 * plate - goal)
                total += (2 * plate)
                currentPlates.add(plate)
            }
        }

        return CalcResult(currentPlates.sortedDescending(), lowError)
    }

    fun calculateOverEstimate(goal: Double): CalcResult? {
        val weightTotal = (bar ?: 0.0) + 2 * plates.sum()
        if (goal > weightTotal){
            return null
        }

        val currentPlates: MutableList<Double> = plates.reversed().toMutableList()
        var total = weightTotal
        var highError = abs(goal - total)
        for (plateIndex in plates.indices.reversed()){
            val plate = currentPlates[plateIndex]
            if (total - 2 * plate >= goal){
                highError = abs(total - 2 * plate - goal)
                total -= (2 * plate)
                currentPlates.removeAt(plateIndex)
            }
        }

        return CalcResult(currentPlates.sortedDescending(), highError)
    }

    companion object
}

data class CalcResult(val set: List<Double>, val error: Double)