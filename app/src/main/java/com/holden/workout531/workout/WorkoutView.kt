package com.holden.workout531.workout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.holden.workout531.LocalUnits

@Composable
fun WorkoutView(workout: Workout){
    Column {
        Text(text = workout.name, style = MaterialTheme.typography.titleLarge)
        Text(text = workout.description)
        LazyColumn {
            items(workout.sets){
                Text(
                    text = "${it.weight}${LocalUnits.current.weightUnit} x ${it.reps}",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
@Preview
fun WorkoutPreview(){
    WorkoutView(
        Workout("Squat", "5 sets of 10", sets = listOf(
            WorkoutSet(10, 100.0),
            WorkoutSet(10, 100.0),
            WorkoutSet(10, 100.0),
            WorkoutSet(10, 100.0),
            WorkoutSet(10, 100.0),
        ))
    )
}