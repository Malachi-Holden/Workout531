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

    fun prChartData() = List(periods.size){ periodIndex ->
        periods[periodIndex] to List(days.size){ dayIndex ->
            days[dayIndex] to List(templatesForDays[dayIndex].size){ workoutIndex ->
                val workout = templatesForDays[dayIndex][workoutIndex].workoutsForPeriods[periodIndex]
                workout.pr?.let {
                    workout.name to it
                }

            }.filterNotNull()
        }.filter { it.second.isNotEmpty() }
    }.filter { it.second.isNotEmpty() }

    companion object
}


@Serializable
@optics
data class WorkoutTemplate(val workoutsForPeriods: List<Workout>){
    companion object
}


