package com.holden.workout531

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.holden.workout531.workout.WorkoutView
import com.holden.workout531.workoutPlan.WorkoutPlanNullableView
import java.lang.IllegalArgumentException

enum class Destination() {
    ChoosePlan, Plan, Detail;

    companion object {
        fun valueOfOrNull(value: String) = try {
            valueOf(value)
        } catch (e: IllegalArgumentException){
            null
        }
    }
}

fun String.routeToDestination() = split("/").firstOrNull()?.let { Destination.valueOfOrNull(it) }

@Composable
fun WorkoutNavHost(navController: NavHostController){
    val viewModel: AppViewmodel = viewModel()
    NavHost(navController = navController, startDestination = Destination.Plan.name){
        composable(Destination.ChoosePlan.name){

        }
        composable(Destination.Plan.name){
            // clear backstack after creating a plan and landing here?
            val plan by viewModel.workoutPlan.collectAsState()
            val index by viewModel.focusedPeriod.collectAsState()
            WorkoutPlanNullableView(
                workoutPlan = plan,
                focusedPeriod = index,
                setFocusedPeriod = {
                    viewModel.setWorkoutPeriod(it)
                },
                onWorkoutClicked = { day, period, workout ->
                    viewModel.setCurrentWorkoutIndex(WorkoutIndex(day, period, workout))
                    navController.navigate(Destination.Detail.name)
                }
            )
        }
        composable(Destination.Detail.name){
            val index by viewModel.currentWorkoutIndex.collectAsState()
            val (day, period, i) = index ?: return@composable // use empty view instead
            val workout = testPlan.workoutsForDay(day, period)[i]
            WorkoutView(workout = workout)
        }
    }
}