package com.holden.workout531.utility

fun <E>List<E>.deletedAt(index: Int) = subList(0, index) + subList(index + 1, size)