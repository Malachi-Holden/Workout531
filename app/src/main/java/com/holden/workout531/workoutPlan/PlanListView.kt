package com.holden.workout531.workoutPlan

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlanListView(plans: List<WorkoutPlan>, currentIndex: Int?, onSelect: (Int)->Unit, onDelete: (Int)->Unit){
    var deleteCandidate: Int? by remember { mutableStateOf(null) }
    LazyColumn{
        items(plans.size){
            Row(
                modifier = Modifier.fillMaxWidth().combinedClickable(
                    onClick = { onSelect(it) },
                    onLongClick = { deleteCandidate = if (deleteCandidate == it) null else it }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = plans[it].name,
                    style = if (currentIndex == it) {
                        MaterialTheme.typography.labelLarge
                    } else {
                        MaterialTheme.typography.labelMedium
                    }
                )
                if (deleteCandidate == it){
                    IconButton(onClick = { onDelete(it) }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete")
                    }
                }
            }

        }
    }
}