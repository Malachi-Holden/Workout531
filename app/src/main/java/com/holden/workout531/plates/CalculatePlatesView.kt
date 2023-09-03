package com.holden.workout531.plates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.holden.workout531.LocalUnits

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatePlatesView(plateSet: PlateSet, goalWeight: Double? = null){
    var goalWeightText by remember { mutableStateOf("") }
    if (goalWeight != null){
        LaunchedEffect(Unit){
            goalWeightText = goalWeight.toString()
        }
    }
    var goalDouble by remember { mutableStateOf(goalWeight) }
    var result: CalcResult? by remember { mutableStateOf(goalDouble?.let { plateSet.calculatePlates(it) }) }
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Calculate plates for")
            TextField(
                value = goalWeightText,
                onValueChange = {
                    goalWeightText = it
                    goalDouble = it.toDoubleOrNull()
                   },
                placeholder = { Text(text = "enter weight") },
                modifier = Modifier
                    .weight(3f)
                    .padding(horizontal = 10.dp),
                maxLines = 1
            )

            Button(onClick = {
                result = goalDouble?.let { plateSet.calculatePlates(it) }
            },
            modifier = Modifier.weight(1f)) {
                Text(text = "Go")
            }
        }
        result?.let { calc ->
            val weightUnits = LocalUnits.current.weightUnit
            val barAmount = plateSet.bar?.toString() ?: "none"
            Text(text = "Bar: $barAmount $weightUnits")
            Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
                LazyColumn(modifier = Modifier.padding(horizontal = 5.dp)) {
                    item {
                        val text = if (calc.exact) {
                            "Exact result"
                        } else {
                            "Low estimate: ${calc.lowError} $weightUnits under"
                        }
                        Text(text = text)
                    }
                    items(calc.lowSet){
                        Text(text = "2 x $it $weightUnits")
                    }
                }
                val highSet = calc.highSet
                if (highSet != null && !calc.exact) {
                    LazyColumn(modifier = Modifier.padding(horizontal = 5.dp)) {
                        item {
                            Text(text = "High estimate ${calc.highError} $weightUnits over")
                        }
                        items(highSet){
                            Text(text = "2 x $it $weightUnits")
                        }
                    }
                }

            }
        }
    }
}

@Composable
@Preview
fun CalculatePlatesPreviewAprox(){
    CalculatePlatesView(
        plateSet = PlateSet(
            bar = 45.0,
            plates = listOf(
                25.0, 15.0, 10.0, 5.0
            )
        ),
//        goalWeight = 117.0
    )
}

@Composable
@Preview
fun CalculatePlatesPreviewExact(){
    CalculatePlatesView(
        plateSet = PlateSet(
            bar = 45.0,
            plates = listOf(
                25.0, 15.0, 10.0, 5.0
            )
        ),
        goalWeight = 115.0
    )
}