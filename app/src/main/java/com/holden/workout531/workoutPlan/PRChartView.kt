package com.holden.workout531.workoutPlan

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.holden.workout531.workout.Workout
import com.holden.workout531.workout.pr

@Composable
fun PRChartView(chart: List<Pair<String, List<Pair<String, List<Pair<String, Int>>>>>>){
    if (chart.isEmpty()) {
        Text(text = "No PRs")
        return
    }
    LazyColumn {
        items(chart.size - 1){
            val period = chart[it]
            PeriodView(period.first, period.second)
            if (chart.size > 1){
                Divider(modifier = Modifier.padding(vertical = 5.dp))
            }
        }

        item {
            val period = chart.last()
            PeriodView(period.first, period.second)
        }

    }
}

@Composable
fun PeriodView(periodName: String, period: List<Pair<String, List<Pair<String, Int>>>>){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 10.dp)
    ) {
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            for (day in period){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .border(2.dp, MaterialTheme.colorScheme.onBackground.copy(alpha = .5f))
                ) {
                    Row {
                        for (workout in day.second){
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            ){
                                Text(text = "PR: ${workout.second}", modifier = Modifier.padding(horizontal = 10.dp))
                                Text(text = workout.first)
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