package org.libprotection.injections.caching

import java.util.Random
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReferenceArray
import kotlin.concurrent.getOrSet

internal class RandomizedLRUCache<TKey, TValue>(private val capacity: Int) {

    private data class CacheRecord<out TKey, out TValue>(val key : TKey, val value : TValue, val counter : AtomicInteger)

    companion object {
        private val random = ThreadLocal<Random>()

        private fun getRandomIndex(maxValue : Int) = random.getOrSet { Random() }.nextInt(maxValue)
    }

    private val cache : ConcurrentHashMap<TKey, CacheRecord<TKey, TValue>> = ConcurrentHashMap()
    private val records : AtomicReferenceArray<CacheRecord<TKey, TValue>?> = AtomicReferenceArray(capacity)

    fun get(key : TKey, factory : (TKey) -> TValue) : TValue
    {
        val record = cache.getOrElse(key) {
            cache.getOrPut(key) {
                CacheRecord(key, factory(key), AtomicInteger())
            }
        }

        val index = getRandomIndex(capacity)
        val oldRecord = records.getAndSet(index, record)

        if (oldRecord == null)
        {
            record.counter.incrementAndGet()
        }
        else if (oldRecord !== record)
        {
            record.counter.incrementAndGet()
            if (oldRecord.counter.decrementAndGet() == 0)
            {
                cache.remove(oldRecord.key)
            }
        }
        return record.value
    }

}