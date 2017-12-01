package com.ptsecurity.libprotection.injections

class Range(val lowerBound: Int, val upperBound: Int) {
    fun length() = upperBound - lowerBound
    fun contains(point: Int) = point in lowerBound..upperBound
    fun containsRange(r: Range) = contains(r.lowerBound) && contains(r.upperBound)
    fun overlaps(r: Range) = lowerBound >= r.lowerBound && upperBound <= r.upperBound
            || contains(r.lowerBound) || contains(r.upperBound)

    override fun toString() = "$lowerBound..${upperBound - 1}"
}
