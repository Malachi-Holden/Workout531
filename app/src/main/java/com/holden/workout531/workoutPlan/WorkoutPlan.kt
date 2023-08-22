package com.holden.workout531.workoutPlan

import com.holden.workout531.workout.Workout
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutPlan(
    val name: String,
    val periods: List<String>,
    val days: List<String>,
    val templatesForDays: List<List<WorkoutTemplate>>
){
    fun workoutsForDay(day: Int, period: Int) = templatesForDays[day].map { it.workoutsForPeriods[period] }
}


@Serializable
data class WorkoutTemplate(val workoutsForPeriods: List<Workout>)


