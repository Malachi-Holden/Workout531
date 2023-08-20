package com.holden.workout531

import androidx.lifecycle.ViewModel
import arrow.optics.optics
import com.holden.workout531.workoutPlan.WorkoutPlan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewmodel: ViewModel() {
    private val _workoutPlan = MutableStateFlow<WorkoutPlan?>(null)
    val workoutPlan = _workoutPlan.asStateFlow()

    private val _currentWorkoutIndex = MutableStateFlow<WorkoutIndex?>(null)
    val currentWorkoutIndex = _currentWorkoutIndex.asStateFlow()

    private val _focusedPeriod = MutableStateFlow<Int?>(null)
    val focusedPeriod = _focusedPeriod.asStateFlow()

    fun setPlan(plan: WorkoutPlan){
        _workoutPlan.value = plan
    }

    fun setWorkoutPeriod(periodIndex: Int?){
        _focusedPeriod.value = periodIndex
    }

    fun setCurrentWorkoutIndex(index: WorkoutIndex){
        _currentWorkoutIndex.value = index
    }
}

@optics
data class WorkoutIndex(val day: Int, val period: Int, val workout: Int){
    companion object
}