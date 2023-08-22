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
import com.holden.workout531.utility.ValidatingTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForBeginnersInitializeView(
    onCreatePlan: (WorkoutPlan)->Unit
){
    val (planName, setPlanName) = remember { mutableStateOf("") }
    val (squatTM, setSquatTM) = remember { mutableStateOf("") }
    var squatValid: Double? by remember { mutableStateOf(null) }
    var squatError by remember { mutableStateOf(false) }

    val (benchTM, setBenchTM) = remember { mutableStateOf("") }
    var benchValid: Double? by remember { mutableStateOf(null) }
    var benchError by remember { mutableStateOf(false) }

    val (deadliftTM, setDeadliftTM) = remember { mutableStateOf("") }
    var deadliftValid: Double? by remember { mutableStateOf(null) }
    var deadliftError by remember { mutableStateOf(false) }

    val (overheadTM, setOverheadTM) = remember { mutableStateOf("") }
    var overheadValid: Double? by remember { mutableStateOf(null) }
    var overheadError by remember { mutableStateOf(false) }

    Column {
        TextField(
            value = planName,
            onValueChange = setPlanName,
            placeholder = { Text(text = "Name of plan") },
            singleLine = true,
        )
        DoubleTextField(
            value = squatTM,
            onValueChange = setSquatTM,
            onInvalid = {
                squatValid = null
            },
            onValidate = {
                squatValid = it
                squatError = false
            },
            placeholder = { Text(text = "Squat training max") },
            singleLine = true,
            isError = squatError
        )
        DoubleTextField(
            value = benchTM,
            onValueChange = setBenchTM,
            onInvalid = {
                benchValid = null
            },
            onValidate = {
                benchValid = it
                benchError = false
            },
            placeholder = { Text(text = "Bench press training max") },
            singleLine = true,
            isError = benchError
        )
        DoubleTextField(
            value = deadliftTM,
            onValueChange = setDeadliftTM,
            onInvalid = {
                deadliftValid = null
            },
            onValidate = {
                deadliftValid = it
                deadliftError = false
            },
            placeholder = { Text(text = "Deadlift training max") },
            singleLine = true,
            isError = deadliftError
        )
        DoubleTextField(
            value = overheadTM,
            onValueChange = setOverheadTM,
            onInvalid = {
                overheadValid = null
            },
            onValidate = {
                overheadValid = it
                overheadError = false
            },
            placeholder = { Text(text = "Overhead press training max") },
            singleLine = true,
            isError = overheadError
        )
        val context = LocalContext.current
        Button(
            onClick = {
                var anyNull = false
                if (squatValid == null){
                    anyNull = true
                    squatError = true
                }
                if (benchValid == null){
                    anyNull = true
                    benchError = true
                }
                if (deadliftValid == null){
                    anyNull = true
                    deadliftError = true
                }
                if (overheadValid == null){
                    anyNull = true
                    overheadError = true
                }
                if (!anyNull){
                    onCreatePlan(forBeginners531(
                        planName,
                        squatValid ?: return@Button,
                        benchValid ?: return@Button,
                        deadliftValid ?: return@Button,
                        overheadValid ?: return@Button,
                    ))
                } else {
                    Toast.makeText(context, "Invalid data", Toast.LENGTH_LONG ).show()
                }
            },
            enabled = squatValid != null && benchValid != null && deadliftValid != null && overheadValid != null
        ) {
            Text(text = "Create")
        }
    }
}