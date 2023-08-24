package com.holden.workout531.workoutPlan

import arrow.optics.optics
import com.holden.workout531.workout.Workout
import kotlinx.serialization.Serializable

@Serializable
@optics
data class WorkoutPlan(
    val name: String,
    val periods: List<String>,
    val days: List<String>,
    val templatesForDays: List<List<WorkoutTemplate>>
){
    fun workoutsForDay(day: Int, period: Int) = templatesForDays[day].map { it.workoutsForPeriods[period] }

    companion object
}


@Serializable
@optics
data class WorkoutTemplate(val workoutsForPeriods: List<Workout>){
    companion object
}


