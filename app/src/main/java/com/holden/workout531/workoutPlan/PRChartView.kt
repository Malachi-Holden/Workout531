package com.holden.workout531.workoutPlan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.holden.workout531.workout.Workout
import com.holden.workout531.workout.pr

@Composable
fun PRChartView(chart: List<Pair<String, List<Pair<String, List<Workout>>>>>){
    LazyRow {
        items(chart){period ->
            PeriodView(period.first, period.second)
        }
    }
}

@Composable
fun PeriodView(periodName: String, period: List<Pair<String, List<Workout>>>){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Row {
            for (day in period){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Row {
                        for (workout in day.second){
                            workout.pr?.let {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ){
                                    Text(text = "PR: $it", modifier = Modifier.padding(horizontal = 10.dp))
                                    Text(text = workout.name)
                                }
                            }
                        }
                    }
                    Text(text = day.first)
                }
            }
        }
        Text(text = periodName)
    }
}

@Composable
@Preview
fun PeriodPreview(){
    val plan = WorkoutPlan.templatesForDays.set(testPlan, testPlan.templatesForDays.map { day ->
        day.map { temp -> WorkoutTemplate.workoutsForPeriods.set(temp, temp.workoutsForPeriods.map {
                it.copy(pr = 6)
            })
        }
    })
    PRChartView(chart = plan.prChartData())
}