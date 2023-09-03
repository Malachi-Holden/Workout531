package com.holden.workout531

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.holden.workout531.preferences.Preferences531
import com.holden.workout531.ui.theme.Workout531Theme
import com.holden.workout531.utility.viewModelWithLambda
import com.holden.workout531.workoutPlan.PlanRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = Preferences531.load(this)
        setContent {
            val viewModel: AppViewmodel = viewModelWithLambda { AppViewmodel(PlanRepository(this), prefs) }
            Workout531Theme {
                CompositionLocalProvider(
                    LocalUnits provides prefs.units
                ) {
                   App(viewModel)
                }
            }
        }
    }
}


/**
 * A workout is a collection of sets, reps, weights, with description
 *
 * There is a "workout" that is really a workout template -- it's a collection of sets and reps with a name,
 * but the weight is variable. It's recorded in the app and you can create your own
 *
 * 531 plan should auto fill all your workouts for a cycle
 * For now, just do the "for beginners" option
 *
 * A "workout plan" is an auto filled module that lets you choose some variables and auto fills the rest
 *
 * When you click on a workout weight, it should open a modal that demonstrates how to create that weight on the bar.
 * There should be settings for remembering what kind of bar configuration the user has, what weights available,
 * bar weight, etc.
 */