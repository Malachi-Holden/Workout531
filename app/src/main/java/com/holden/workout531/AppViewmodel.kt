package com.holden.workout531

import androidx.lifecycle.ViewModel
import com.holden.workout531.workoutPlan.WorkoutPlan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewmodel: ViewModel() {
    private val _workoutPlan = MutableStateFlow<WorkoutPlan?>(null)
    val workoutPlan = _workoutPlan.asStateFlow()

    private val _currentWorkoutIndex = MutableStateFlow<WorkoutIndex?>(null)
    val currentWorkoutIndex = _currentWorkoutIndex.asStateFlow()

    fun setPlan(plan: WorkoutPlan){
        _workoutPlan.value = plan
    }

    fun setWorkoutOutIndex(index: WorkoutIndex?){
        _currentWorkoutIndex.value = index
    }
}

data class WorkoutIndex(val day: Int, val period: Int, val workout: Int)