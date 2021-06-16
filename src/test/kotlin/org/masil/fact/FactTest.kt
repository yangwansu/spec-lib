package org.masil.fact

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test



class FactTest {

    private lateinit var facts: Fact

    @BeforeEach
    fun setup() {
        facts = Fact()
    }

    @Test
    fun `facts must have unique name`() {
        facts.put("foo", 1)
        facts.put("foo", 2)

        assertTrue { facts.size == 1 }
        assertTrue { facts["foo"] == 2 }
    }

    @Test
    fun `return of put is old value`() {
        val o1 = facts.put("foo", 1)
        val o2 = facts.put("foo", 2)

        assertNull(o1)
        assertTrue(o2 == 1)
    }

    @Test
    fun `remove fact`() {
        facts.put("foo", 1)
        facts.remove("foo")

        assertTrue { facts.isEmpty() }
    }

    @Test
    fun `return of remove is old value`() {
        facts.put("foo", 1)

        assertTrue { facts.remove("foo") == 1 }
        assertNull(facts.remove("foo"))
    }

    @Test
    fun `get from fact`() {
        facts.put("foo", 1)
        assertTrue { facts["foo"] == 1 }
    }

    @Test
    fun `convert to map`() {
        facts["foo"] = 1
        facts["bar"] = 2

        val map = facts.asMap()
        assertTrue { map is HashMap }
        assertEquals(map, hashMapOf("foo" to 1, "bar" to 2))
    }

    @Test
    fun `put with empty name`() {
        assertThrows(IllegalArgumentException::class.java) {
            facts.put("", 1)
        }
    }

    @Test
    fun `remove with empty name`() {
        assertThrows(IllegalArgumentException::class.java) {
            facts.remove("")
        }
    }

    @Test
    fun `get with empty name`() {
        assertThrows(IllegalArgumentException::class.java) {
            facts[""]
        }
    }

    @Test
    internal fun name() {
        facts["foo"] = null

        assertTrue { facts.size == 1 }
    }
}