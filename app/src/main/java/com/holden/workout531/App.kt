package com.holden.workout531

import androidx.compose.runtime.Composable
import com.holden.workout531.workoutPlan.ForBeginners531
import com.holden.workout531.workoutPlan.WorkoutPlanView

val testPlan = ForBeginners531(100.0, 100.0, 100.0, 100.0)

@Composable
fun App(){
    WorkoutPlanView(workoutPlan = testPlan)
}