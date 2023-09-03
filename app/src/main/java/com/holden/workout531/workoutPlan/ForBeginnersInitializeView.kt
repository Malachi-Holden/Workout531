package com.holden.workout531.workoutPlan

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.holden.workout531.utility.DoubleTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForBeginnersInitializeView(
    onCreatePlan: (WorkoutPlan)->Unit
){
    val (planName, setPlanName) = remember { mutableStateOf("") }
    var squatTM: Double? by remember { mutableStateOf(null) }

    var benchTM: Double? by remember { mutableStateOf(null) }

    var deadliftTM: Double? by remember { mutableStateOf(null) }

    var overheadTM: Double? by remember { mutableStateOf(null) }

    Column {
        TextField(
            value = planName,
            onValueChange = setPlanName,
            placeholder = { Text(text = "Name of plan") },
            singleLine = true,
        )
        DoubleTextField(
            onDoubleResult = {
                squatTM = it
            },
            placeholder = { Text(text = "Squat training max") },
            singleLine = true
        )
        DoubleTextField(
            onDoubleResult = {
                benchTM = it
            },
            placeholder = { Text(text = "Squat training max") },
            singleLine = true
        )
        DoubleTextField(
            onDoubleResult = {
                deadliftTM = it
            },
            placeholder = { Text(text = "Squat training max") },
            singleLine = true
        )
        DoubleTextField(
            onDoubleResult = {
                overheadTM = it
            },
            placeholder = { Text(text = "Squat training max") },
            singleLine = true
        )
        Button(
            onClick = {
                onCreatePlan(forBeginners531(
                    planName,
                    squatTM ?: return@Button,
                    benchTM ?: return@Button,
                    deadliftTM ?: return@Button,
                    overheadTM ?: return@Button,
                ))

            },
            enabled = squatTM != null && benchTM != null && deadliftTM != null && overheadTM != null
        ) {
            Text(text = "Create")
        }
    }
}