package com.holden.workout531.workoutPlan

import com.holden.workout531.workout.Workout

interface WorkoutPlan{
    val periods: List<String>
    val days: List<String>
    val templatesForDays: List<List<WorkoutTemplate>>
    fun workoutsForDay(day: Int, period: Int) = templatesForDays[day].map { it.workoutsForPeriods[period] }
}


interface WorkoutTemplate {
    val workoutsForPeriods: List<Workout>
}

