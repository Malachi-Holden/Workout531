package com.holden.workout531.workoutPlan

import com.holden.workout531.workout.Workout
import com.holden.workout531.workout.WorkoutSet


fun forBeginners531(
    planName: String,
    squatTM: Double,
    benchTM: Double,
    deadliftTM: Double,
    overheadTM: Double
) = WorkoutPlan(
    name = planName,
    periods = listOf(
        "Week 1", "Week 2", "Week 3"
    ),
    days = listOf(
        "Day 1", "Day 2", "Day 3"
    ),
    templatesForDays = listOf(
        listOf(
            template531ForBeginners(squatTM, "Squat"),
            template531ForBeginners(benchTM, "Bench")
        ),
        listOf(
            template531ForBeginners(deadliftTM, "Deadlift"),
            template531ForBeginners(overheadTM, "Overhead")
        ),
        listOf(
            template531ForBeginners(squatTM, "Squat"),
            template531ForBeginners(benchTM, "Bench")
        )
    )
)

fun template531ForBeginners(tm: Double, name: String) = WorkoutTemplate (
    workoutsForPeriods =
        listOf(
            // include accessory stuff
            Workout(name,"Warmup then 3 x 5",
                listOf(
                    WorkoutSet(5, .4 * tm),
                    WorkoutSet(5, .5 * tm),
                    WorkoutSet(5, .6 * tm),
                    WorkoutSet(5, .65 * tm),
                    WorkoutSet(5, .75 * tm),
                    WorkoutSet(5, .85 * tm),
                    WorkoutSet(5, .65 * tm, 5)
                )
            ),
            Workout(name,"Warmup then 3 x 3",
                listOf(
                    WorkoutSet(5, .4 * tm),
                    WorkoutSet(5, .5 * tm),
                    WorkoutSet(5, .6 * tm),
                    WorkoutSet(3, .7 * tm),
                    WorkoutSet(3, .8 * tm),
                    WorkoutSet(3, .9 * tm),
                    WorkoutSet(5, .7 * tm, 5)
                )
            ),
            Workout(name,"Warmup then 5, 3, 1",
                listOf(
                    WorkoutSet(5, .4 * tm),
                    WorkoutSet(5, .5 * tm),
                    WorkoutSet(5, .6 * tm),
                    WorkoutSet(5, .75 * tm),
                    WorkoutSet(3, .85 * tm),
                    WorkoutSet(1, .95 * tm),
                    WorkoutSet(5, .75 * tm, 5)
                )
            )
        )
)