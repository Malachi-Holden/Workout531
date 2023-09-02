package com.holden.workout531.utility

import arrow.optics.Optional
import kotlinx.coroutines.flow.MutableStateFlow

fun <T, S>MutableStateFlow <T?>.modifyNullable(optional: Optional<T, S>, transformation: (S)->S){
    value = value?.let { optional.modify(it, transformation) }
}

fun <T, S>MutableStateFlow<T>.set(optional: Optional<T, S>, newValue: S){
    value = optional.set(value, newValue)
}