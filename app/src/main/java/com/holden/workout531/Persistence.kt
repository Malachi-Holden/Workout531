package com.holden.workout531

import android.content.Context
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException


@OptIn(ExperimentalSerializationApi::class)
inline fun <reified A>saveLocalObject(context: Context, obj: A, file: String){
    val json = Json.encodeToString(obj)
    context.openFileOutput(file, Context.MODE_PRIVATE).use {
        it.write(json.toByteArray())
    }
}

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified A>getLocalObject(context: Context, file: String): A?{
    return try {
        val json = context.openFileInput(file).bufferedReader().use {
            it.readText()
        }

        Json.decodeFromString(json)
    } catch (e: FileNotFoundException){
        null
    }
}