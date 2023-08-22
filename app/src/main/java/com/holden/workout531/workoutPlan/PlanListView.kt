package com.holden.workout531.workoutPlan

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PlanListView(plans: List<WorkoutPlan>, currentIndex: Int?, onSelect: (Int)->Unit){
    LazyColumn{
        items(plans.size){
            Button(onClick = { onSelect(it) }) {
                Text(text = plans[it].name,
                    style = if (currentIndex == it) {
                        MaterialTheme.typography.labelLarge
                    } else {
                        MaterialTheme.typography.labelMedium
                    }
                )
            }
            // long press to show delete option
        }
    }
}