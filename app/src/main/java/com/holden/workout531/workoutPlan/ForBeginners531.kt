package com.holden.workout531.workoutPlan

import com.holden.workout531.workout.Workout
import com.holden.workout531.workout.WorkoutSet


class ForBeginners531(
    squatTM: Double,
    deadliftTM: Double,
    overheadTM: Double,
    benchTM: Double
): WorkoutPlan{

    override val periods: List<String> = listOf(
        "Week 1", "Week 2", "Week 3"
    )

    override val days: List<String> = listOf(
        "Day 1", "Day 2", "Day 3"
    )

    override val templatesForDays = listOf<List<WorkoutTemplate>>(
        listOf(
            Template531(squatTM, "Squat"),
            Template531(benchTM, "Bench")
        ),
        listOf(
            Template531(deadliftTM, "Deadlift"),
            Template531(overheadTM, "Overhead")
        ),
        listOf(
            Template531(squatTM, "Squat"),
            Template531(benchTM, "Bench")
        )
    )
}

class Template531(tm: Double, val name: String): WorkoutTemplate {
    override val workoutsForPeriods: List<Workout> =
        listOf(
            Workout(name,"Warmup then 3 x 5",
                listOf(
                    WorkoutSet(5, .4 * tm),
                    WorkoutSet(5, .5 * tm),
                    WorkoutSet(5, .6 * tm),
                    WorkoutSet(5, .65 * tm),
                    WorkoutSet(5, .75 * tm),
                    WorkoutSet(5, .85 * tm)
                )
            ),
            Workout(name,"Warmup then 3 x 3",
                listOf(
                    WorkoutSet(5, .4 * tm),
                    WorkoutSet(5, .5 * tm),
                    WorkoutSet(5, .6 * tm),
                    WorkoutSet(3, .7 * tm),
                    WorkoutSet(3, .8 * tm),
                    WorkoutSet(3, .9 * tm)
                )
            ),
            Workout(name,"Warmup then 5, 3, 1",
                listOf(
                    WorkoutSet(5, .4 * tm),
                    WorkoutSet(5, .5 * tm),
                    WorkoutSet(5, .6 * tm),
                    WorkoutSet(5, .75 * tm),
                    WorkoutSet(3, .85 * tm),
                    WorkoutSet(1, .95 * tm)
                )
            )
        )
    }