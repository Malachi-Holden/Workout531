package com.holden.workout531

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.holden.workout531.ui.theme.Workout531Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Workout531Theme {
                CompositionLocalProvider(LocalUnits provides Units(weightUnit = "lbs")) {
                   App()
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