package com.holden.workout531

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.holden.workout531.workout.WorkoutView
import com.holden.workout531.workoutPlan.WorkoutPlanNullableView
import com.holden.workout531.workoutPlan.WorkoutPlanView
import java.lang.IllegalArgumentException

enum class Destination(val args: String = "") {
    ChoosePlan(), Plan(), Detail("{day}/{period}/{workout}");

    fun routeString() = "${name}/${args}"

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
    NavHost(navController = navController, startDestination = "${Destination.Plan.name}/"){
        composable(Destination.ChoosePlan.routeString()){

        }
        composable(Destination.Plan.routeString()){backStackEntry ->
            // clear backstack after creating a plan and landing here?
            val plan by viewModel.workoutPlan.collectAsState()
            WorkoutPlanNullableView(workoutPlan = plan, onWorkoutClicked = { day, period, workout ->
                navController.navigate("${Destination.Detail.name}/$day/$period/$workout")
            })
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