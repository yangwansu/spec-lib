package org.masil.fact

import java.io.Serializable

/**
 * Condition, Action 수행 시 사용할 정보를 나타냅니다.
 *
 * @author debop
 */
open class Fact: Iterable<Map.Entry<String, Any?>>, Serializable {

    private val facts = hashMapOf<String, Any?>()

    fun put(name: String, fact: Any?): Any? {
        require(name.isNotBlank()) { "name must not be empty." }
        return facts.put(name, fact)
    }

    fun remove(name: String): Any? {
        require(name.isNotBlank()) { "name must not be empty." }
        return facts.remove(name)
    }

    fun clear() {
        facts.clear()
    }

    val size: Int get() = facts.size

    fun isEmpty(): Boolean = facts.isEmpty()

    operator fun get(name: String): Any? {
        require(name.isNotBlank()) { "name must not be empty." }
        return facts[name]
    }

    operator fun set(name: String, value: Any?) {
        require(name.isNotBlank()) { "name must not be empty." }
        facts[name] = value
    }

    fun asMap(): Map<String, Any?> = facts

    override fun iterator(): Iterator<Map.Entry<String, Any?>> {
        return facts.iterator()
    }

    fun printFacts(): String = buildString {
        appendln()
        append("Fact={")
        joinTo(this) { "  $it" }
        append("}")
    }

    override fun toString(): String = buildString {
        append("Fact={")
        joinTo(this) { it.toString() }
        append("}")
    }
}

fun factOf(): Fact = Fact()
fun factOf(pair: Pair<String, Any?>): Fact = Fact().apply { put(pair.first, pair.second) }
fun factOf(vararg pairs: Pair<String, Any?>): Fact = Fact().apply {
    pairs.forEach {
        put(it.first, it.second)
    }
}
