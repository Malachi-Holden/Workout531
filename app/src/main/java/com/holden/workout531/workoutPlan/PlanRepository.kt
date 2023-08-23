package com.holden.workout531.workoutPlan

import android.content.Context
import com.holden.workout531.getLocalObject
import com.holden.workout531.saveLocalObject
import kotlinx.serialization.Serializable

class PlanRepository(context: Context) {
    var planState = getLocalObject<AppSaveState>(context, WORKOUT_PLAN_FILE)

    fun persistPlans(context: Context, index: Int?, plans: List<WorkoutPlan>){
        planState = AppSaveState(index, plans)
        saveLocalObject(context, planState, WORKOUT_PLAN_FILE)
    }

    companion object {
        val WORKOUT_PLAN_FILE = "WORKOUT_PLAN_FILE"
    }
}

@Serializable
data class AppSaveState(val currentPlanIndex: Int?, val plans: List<WorkoutPlan>)