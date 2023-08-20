package com.holden.workout531.workoutPlan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.holden.workout531.testPlan

@Composable
fun WorkoutPlanView(workoutPlan: WorkoutPlan, onWorkoutClicked: (Int, Int, Int) -> Unit){
    var focusedIndex by remember {
        mutableIntStateOf(-1) // remember this when you come back to it
    }
    LazyColumn {
        items(workoutPlan.periods.size){
            Column(
                modifier = Modifier.clickable {
                    focusedIndex = if (focusedIndex == it) -1 else it
                }
            ) {
                val periodTitleStyle = if (focusedIndex == it) {
                    MaterialTheme.typography.titleLarge
                } else {
                    MaterialTheme.typography.labelLarge
                }
                Text(text = workoutPlan.periods[it], style = periodTitleStyle)

                if (focusedIndex == it){
                    WorkoutPeriodView(it, workoutPlan, onWorkoutClicked = onWorkoutClicked)
                }
            }

        }
    }
}

@Composable
fun WorkoutPeriodView(period: Int, workoutPlan: WorkoutPlan, onWorkoutClicked: (Int, Int, Int)->Unit){
    Column {
        for (dayIndex in 0 until workoutPlan.days.size) {
            val workouts = workoutPlan.workoutsForDay(dayIndex, period)
            Column {
                Text(text = workoutPlan.days[dayIndex])
                for (workoutIndex in workouts.indices){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            onWorkoutClicked(dayIndex, period, workoutIndex)
                        }
                    ) {
                        Text(text = workouts[workoutIndex].name, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(5.dp))
                        Text(text = workouts[workoutIndex].description)
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun WorkoutPlanPreview(){
    WorkoutPlanView(workoutPlan = testPlan, onWorkoutClicked = {_,_,_->})
}

@Preview
@Composable
fun WorkoutPeriodPreview(){
    WorkoutPeriodView(0, testPlan, onWorkoutClicked =  {_,_,_->})
}