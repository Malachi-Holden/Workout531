package com.holden.workout531.preferences

import android.content.Context
import com.holden.workout531.Units
import com.holden.workout531.getLocalObject
import com.holden.workout531.plates.PlateSet
import com.holden.workout531.saveLocalObject
import kotlinx.serialization.Serializable

@Serializable
data class Preferences531(val units: Units, val plateSet: PlateSet) {

    fun save(context: Context){
        saveLocalObject(context, this, PREFERENCES_FILE)
    }

    companion object {
        val PREFERENCES_FILE = "PREFERENCES_FILE"

        val defaultPlates = PlateSet(45.0, listOf(45.0, 25.0, 10.0, 5.0, 2.5))

        fun defaultPrefs() = Preferences531(Units(weightUnit = "lbs"), defaultPlates)

        fun load(context: Context): Preferences531
        = getLocalObject<Preferences531>(context, PREFERENCES_FILE) ?: defaultPrefs().apply { save(context) }
    }
}

