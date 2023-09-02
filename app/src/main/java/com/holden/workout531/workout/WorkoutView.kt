package com.holden.workout531.workout

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.holden.workout531.LocalUnits

@Composable
fun WorkoutView(workout: Workout, onSavePR: (Int?)->Unit){
    Column {
        Text(text = workout.name, style = MaterialTheme.typography.titleLarge)
        Text(text = workout.description)
        LazyColumn {
            items(workout.sets){
                val setsText = if (it.sets > 1) "${it.sets} sets of " else ""
                Text(
                    text = "$setsText${it.weight}${LocalUnits.current.weightUnit} x ${it.reps}",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        ValidatePR(workout.pr, onSavePR)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ValidatePR(pr: Int?, onSavePR: (Int?) -> Unit) {
    var prText by remember { mutableStateOf(pr?.toString() ?: "") }
    var validPR: Int? by remember { mutableStateOf(null) }
    var prIsCorrect by remember { mutableStateOf(true) }
    var showPRError by remember { mutableStateOf(false) }
    Row {
        TextField(
            value = prText,
            onValueChange = {
                prText = it
                validPR = prText.toIntOrNull()
                prIsCorrect = prText.isBlank() || validPR != null
                if (prIsCorrect) {
                    showPRError = false
                }
            },
            isError = showPRError,
            placeholder = { Text(text = "PR for this workout") },
        )
        val context = LocalContext.current
        val focusManager = LocalFocusManager.current
        Button(onClick = {
            if (!prIsCorrect) {
                showPRError = true
                Toast.makeText(context, "Must be number, or blank", Toast.LENGTH_SHORT).show()
            } else {
                onSavePR(validPR)
                focusManager.clearFocus()
            }
        }) {
            Text(text = "Save")
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
        )),
        onSavePR = {

        }
    )
}