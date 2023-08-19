package com.holden.workout531.workout

import arrow.optics.optics

@optics
data class Workout(val name: String, val description: String = "", val sets: List<WorkoutSet>) {
    companion object
}

@optics
data class WorkoutSet(val reps: Int, val weight: Double){
    // have a way to set number of sets, so you can have easy repeated sets
    companion object
}

