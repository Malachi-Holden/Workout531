package com.holden.workout531

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.holden.workout531.workout.WorkoutView
import com.holden.workout531.workoutPlan.WorkoutPlanView

enum class Destination(val args: String = "") {
    ChoosePlan(), Plan("{planId}"), Detail("{day}/{period}/{workout}");

    fun routeString() = "${name}/${args}"
}

@Composable
fun WorkoutNavHost(navController: NavHostController){
    NavHost(navController = navController, startDestination = "${Destination.Plan.name}/hello"){
        composable(Destination.ChoosePlan.routeString()){

        }
        composable(Destination.Plan.routeString()){backStackEntry ->
            WorkoutPlanView(workoutPlan = testPlan, onWorkoutClicked = { day, period, workout ->
                navController.navigate("${Destination.Detail.name}/$day/$period/$workout")
            }) // get plan from id from viewmodel
        }
        composable(Destination.Detail.routeString()){backStackEntry ->
            val day = backStackEntry.arguments?.getString("day")?.toIntOrNull() ?: return@composable
            val period = backStackEntry.arguments?.getString("period")?.toIntOrNull() ?: return@composable
            val i = backStackEntry.arguments?.getString("workout")?.toIntOrNull() ?: return@composable
            val workout = testPlan.workoutsForDay(day, period)[i]
            WorkoutView(workout = workout)
        }
    }
}