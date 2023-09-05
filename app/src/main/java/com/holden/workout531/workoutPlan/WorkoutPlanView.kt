package com.holden.workout531.workoutPlan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun WorkoutPlanNullableView(workoutPlan: WorkoutPlan?, focusedPeriod: Int?, setFocusedPeriod: (Int?)->Unit, onWorkoutClicked: (Int, Int, Int) -> Unit){
    if (workoutPlan == null){
        WorkoutPlanEmptyView()
    } else {
        WorkoutPlanView(workoutPlan, focusedPeriod, setFocusedPeriod, onWorkoutClicked)
    }
}

@Composable
fun WorkoutPlanEmptyView(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(
            text = "No plan selected",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun WorkoutPlanView(workoutPlan: WorkoutPlan, focusedPeriod: Int?, setFocusedPeriod: (Int?)->Unit, onWorkoutClicked: (Int, Int, Int) -> Unit){
    LazyColumn {
        items(workoutPlan.periods.size){
            Column(
                modifier = Modifier.clickable {
                    setFocusedPeriod(if (focusedPeriod == it) null else it)
                }.fillMaxWidth()
            ) {
                val periodTitleStyle = if (focusedPeriod == it) {
                    MaterialTheme.typography.titleLarge
                } else {
                    MaterialTheme.typography.labelLarge
                }
                Text(text = workoutPlan.periods[it], style = periodTitleStyle, modifier = Modifier.padding(10.dp))

                if (focusedPeriod == it){
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
                        }.fillMaxWidth()
                    ) {
                        Text(text = workouts[workoutIndex].name, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(10.dp))
                        Text(text = workouts[workoutIndex].description)
                    }
                }

            }
        }
    }
}

val testPlan = forBeginners531("test plan",100.0, 100.0, 100.0, 100.0)

@Preview
@Composable
fun WorkoutPlanPreview(){
    WorkoutPlanView(workoutPlan = testPlan, 1, {}, onWorkoutClicked = {_,_,_->})
}

@Preview
@Composable
fun WorkoutPeriodPreview(){
    WorkoutPeriodView(0, testPlan, onWorkoutClicked =  {_,_,_->})
}