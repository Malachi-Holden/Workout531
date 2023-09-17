package com.holden.workout531.workout

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.holden.workout531.LocalUnits
import com.holden.workout531.utility.ValidatingTextField
import com.holden.workout531.utility.roundTo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WorkoutView(workout: Workout, onSavePR: (Int?)->Unit, onShowCalculatePlates: (Double)->Unit){
    Column {
        Text(text = workout.description)
        LazyColumn {
            items(workout.sets){
                Box(modifier = Modifier.combinedClickable(onClick = {}, onLongClick = {
                    onShowCalculatePlates(it.weight)
                }).fillMaxWidth()) {
                    val setsText = if (it.sets > 1) "${it.sets} sets of " else ""
                    Text(
                        text = "$setsText${it.weight.roundTo(2)}${LocalUnits.current.weightUnit} x ${it.reps}",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
        ValidatePR(workout.pr, onSavePR)
    }
}

@Composable
private fun ValidatePR(pr: Int?, onSavePR: (Int?) -> Unit) {
    var resultPR: Int? by remember { mutableStateOf(null) }
    var prIsCorrect by remember { mutableStateOf(true) }
    Row {
        ValidatingTextField(
            initialValue = pr?.toString() ?: "",
            transform = {
                prIsCorrect = it.toIntOrNull() != null || it.isBlank()
                it.toIntOrNull()
            },
            onTransform = {
                resultPR = it
            },
            validate = { it.toIntOrNull() != null || it.isBlank() },
            placeholder = { Text(text = "PR for this workout") }
        )
        val context = LocalContext.current
        val focusManager = LocalFocusManager.current
        Button(onClick = {
            if (prIsCorrect) {
                onSavePR(resultPR)
                focusManager.clearFocus()
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
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

        }, {}
    )
}