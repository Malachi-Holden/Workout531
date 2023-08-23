package com.holden.workout531

import android.content.Context
import androidx.lifecycle.ViewModel
import arrow.optics.optics
import com.holden.workout531.utility.deletedAt
import com.holden.workout531.workoutPlan.PlanRepository
import com.holden.workout531.workoutPlan.WorkoutPlan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewmodel(val repository: PlanRepository): ViewModel() {
    private val _plans = MutableStateFlow(
        repository.planState?.plans ?: listOf()
    )
    val plans = _plans.asStateFlow()

    private val _currentPlanIndex = MutableStateFlow(repository.planState?.currentPlanIndex)
    val currentPlanIndex = _currentPlanIndex.asStateFlow()

    private val _workoutPlan = MutableStateFlow(repository.planState?.currentPlanIndex?.let { repository.planState?.plans?.get(it) } )
    val workoutPlan = _workoutPlan.asStateFlow()

    private val _currentWorkoutIndex = MutableStateFlow<WorkoutIndex?>(null)
    val currentWorkoutIndex = _currentWorkoutIndex.asStateFlow()

    private val _focusedPeriod = MutableStateFlow<Int?>(null)
    val focusedPeriod = _focusedPeriod.asStateFlow()


    fun createPlan(context: Context, plan: WorkoutPlan){
        _workoutPlan.value = plan
        _plans.value += plan
        _currentPlanIndex.value = _plans.value.size - 1
        persistPlans(context)
    }

    fun setCurrentPlanIndex(context: Context, index: Int){
        _currentPlanIndex.value = index
        _workoutPlan.value = plans.value[index]
        persistPlans(context)
    }

    fun setWorkoutPeriod(periodIndex: Int?){
        _focusedPeriod.value = periodIndex
    }

    fun setCurrentWorkoutIndex(index: WorkoutIndex){
        _currentWorkoutIndex.value = index
    }

    fun deletePlan(context: Context, index: Int){
        if (workoutPlan.value == plans.value.getOrNull(index)){
            _workoutPlan.value = null
            _currentPlanIndex.value = null
        }
        val currentIndex = currentPlanIndex.value
        if (currentIndex != null && currentIndex > index){
            _currentPlanIndex.value = currentIndex - 1
        }
        _plans.value = plans.value.deletedAt(index)
        persistPlans(context)
    }

    fun persistPlans(context: Context) = repository.persistPlans(context, currentPlanIndex.value, plans.value)
}

@optics
data class WorkoutIndex(val day: Int, val period: Int, val workout: Int){
    companion object
}