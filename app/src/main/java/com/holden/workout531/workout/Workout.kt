package com.holden.workout531.workout

import arrow.optics.optics
import kotlinx.serialization.Serializable

@Serializable
@optics
data class Workout(val name: String, val description: String = "", val sets: List<WorkoutSet>, val pr: Int? = null) {
    companion object
}

@Serializable
@optics
data class WorkoutSet(val reps: Int, val weight: Double, val sets: Int = 1){
    // have a way to set number of sets, so you can have easy repeated sets
    companion object
}

