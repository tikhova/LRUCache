package test.kotlin

import main.java.LRUCache
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LRUCacheTests {
    private val capacity = 10

    @Test
    fun `Increments size on put`() {
        val cache = LRUCache<Int>(capacity)

        for (i in 1 .. capacity) {
            cache.put(i)
            assertEquals(cache.size(), i)
        }
    }

    @Test
    fun `Contains inserted value`() {
        val cache = LRUCache<Int>(capacity)
        val value = 5
        cache.put(value)
        val result = cache.get(value.hashCode())
        assertEquals(value, result)
    }

    @Test
    fun `Fails on empty cache`() {
        try {
            val cache = LRUCache<Int>(capacity)
            val value = 5
            cache.get(value.hashCode())
        } catch (e: AssertionError) {
            return
        }
        throw AssertionError()
    }

    @Test
    fun `Doesn't contain uninserted value`() {
        val cache = LRUCache<Int>(capacity)
        val value = 5
        val fakeValue = 6
        cache.put(value)

        try {
            cache.get(fakeValue.hashCode())
        } catch (e: AssertionError) {
            return
        }
    }

    @Test
    fun `Does not overflow`() {
        val cache = LRUCache<Int>(capacity)

        for (i in 0 .. capacity) {
            cache.put(i)
        }

        assertEquals(cache.size(), capacity)
    }

    @Test
    fun `Doesn't delete least recently used cache`() {
        val cache = LRUCache<Int>(capacity)
        for (i in 1 .. capacity) {
            cache.put(i)
        }
        cache.get(1.hashCode())
        cache.put(capacity + 1)
        cache.get(1.hashCode())
    }

    @Test
    fun `Deletes the oldest cache`() {
        val cache = LRUCache<Int>(capacity)

        for (i in 0 .. capacity) {
            cache.put(i)
        }

        try {
            cache.get(0.hashCode())
        } catch (e: AssertionError) {
            return
        }
    }

    @Test
    fun `Deletes the oldest unused cache`() {
        val cache = LRUCache<Int>(capacity)

        for (i in 1 .. capacity) {
            cache.put(i)
        }

        cache.get(1.hashCode())
        cache.put(capacity + 1)

        try {
            cache.get(2.hashCode())
        } catch (e: AssertionError) {
            return
        }
    }
}
