package com.holden.workout531.preferences

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.holden.workout531.Units
import com.holden.workout531.plates.PlateSet
import com.holden.workout531.utility.deletedAt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesView(preferences: Preferences531){
    val context = LocalContext.current
    val (weightUnits, setWeightUnits) = remember { mutableStateOf(preferences.units.weightUnit) }
    val (plateSet, setPlateSet) = remember {
        mutableStateOf(preferences.plateSet)
    }
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Preferred Weight Units:")
            TextField(value = weightUnits, onValueChange = setWeightUnits)
        }
        Text(text = "Available weights")
        SelectPlatesView(plateSet = plateSet, onPlateSetChanged = setPlateSet)
        Button(onClick = {
            Preferences531(Units(weightUnits), plateSet).save(context)
            Toast.makeText(context, "Preferences Saved!", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Save preferences")
        }
    }
}

@Composable
fun SelectPlatesView(plateSet: PlateSet, onPlateSetChanged: (PlateSet)->Unit){
    LazyColumn {
        item {
            if (plateSet.bar != null) {
                PlateItemView(text = "Bar: ${plateSet.bar}", onDelete = {
                    onPlateSetChanged(plateSet.copy(bar = null))
                })
            } else {
                ChooseDoubleView(onChosen = {
                    onPlateSetChanged(plateSet.copy(bar = it))
                }, buttonText = "Set bar weight", default = 45.0)
            }
        }
        items(plateSet.plates.size){
            val weight = plateSet.plates[it]
            PlateItemView(text = "2 x $weight", onDelete = {
                onPlateSetChanged(plateSet.copy(plates = plateSet.plates.deletedAt(it)))
            })
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Add 2 x")
                ChooseDoubleView(onChosen = {
                    onPlateSetChanged(plateSet.copy(plates = (plateSet.plates + it).sortedDescending()))
                }, buttonText = "Add")
            }
        }
    }
}

@Composable
fun PlateItemView(text: String, onDelete: ()->Unit){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = text)
        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseDoubleView(onChosen: (Double)->Unit, buttonText: String, default: Double? = null){
    var barText by remember { mutableStateOf(default?.toString() ?: "") }
    var barWeight: Double? by remember { mutableStateOf(barText.toDoubleOrNull()) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        TextField(value = barText,
            onValueChange = {
                barText = it
                barWeight = it.toDoubleOrNull()
            },
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = {
                barWeight?.let(onChosen)
            },
            enabled = barWeight != null
        ) {
            Text(text = buttonText)
        }
    }
}

@Preview
@Composable
fun PreferencesPreview(){
    PreferencesView(preferences = Preferences531.defaultPrefs())
}