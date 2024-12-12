package org.garethellis.adventofcode.twentyfour

fun Int.digits(): Int = this.toString().length
fun Long.digits(): Int = this.toString().length

val Int.isEven: Boolean
    get() = this % 2 == 0
val Long.isEven: Boolean
    get() = this % 2 == 0L
val Int.isOdd: Boolean
    get() = this % 2 != 0
val Long.isOdd: Boolean
    get() = this % 2 != 0L
