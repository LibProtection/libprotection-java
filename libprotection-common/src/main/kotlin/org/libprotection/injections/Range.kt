package org.libprotection.injections

data class Range(val lowerBound: Int, val upperBound: Int) {

    val length = upperBound - lowerBound

    fun contains(point: Int) = point in (lowerBound until upperBound)

    fun contains(range: Range) = contains(range.lowerBound) && contains(range.upperBound)

    fun overlaps(range: Range) =
         (lowerBound >= range.lowerBound && upperBound <= range.upperBound
                || contains(range.lowerBound)
                || contains(range.upperBound - 1))

    override fun toString() = if (length != 0) "[$lowerBound..$upperBound)" else "[$lowerBound)"
}
