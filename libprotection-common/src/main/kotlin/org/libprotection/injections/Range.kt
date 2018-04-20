package org.libprotection.injections

data class Range(val lowerBound: Int, val upperBound: Int) {

    val length = upperBound - lowerBound + 1

    fun contains(point: Int) = point in lowerBound..upperBound

    fun contains(range: Range) = contains(range.lowerBound) && contains(range.upperBound)

    fun overlaps(range: Range) =
         (lowerBound >= range.lowerBound && upperBound <= range.upperBound
                || contains(range.lowerBound)
                || contains(range.upperBound))

    override fun toString() = "[$lowerBound..$upperBound]"
}
