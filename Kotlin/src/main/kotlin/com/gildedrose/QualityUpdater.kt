package com.gildedrose

import kotlin.math.max
import kotlin.math.min

/**
 * Helper interface which returns the new quality value and ensure that this new value is never greater than the max
 * and lower than the min.
 * Examples:
 *   1) min = 2 and max = 15
 *     ```kotlin
 *       `10 increaseBy 3` // returns 13
 *     ```
 *   2) min = 2 and = max 15
 *     ```kotlin
 *       `14 increaseBy 3` // returns 15
 *     ```
 *   3) min = 2 and max = 15
 *     ```kotlin
 *       `4 decreaseBy 3` // return 2
 *     ```
 */
interface QualityUpdater {

    val min: Int
        get() = 0
    val max: Int
        get() = 50

    infix fun Int.increaseBy(addend: Int): Int {
        return min(this + addend, max)
    }

    infix fun Int.decreaseBy(subtrahend: Int): Int {
        return max(this - subtrahend, min)
    }
}
