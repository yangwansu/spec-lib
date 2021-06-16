package org.masil.spec

/**
 * Created by yangwansu on 2019-07-03.
 */

inline fun <reified T> spec(noinline predicate: (T) -> Boolean): Spec<T> = Spec.create(predicate)

val alwaysTrue = spec<Any> { true }
val alwaysFalse = !alwaysTrue


fun <T> every(vararg specs: Spec<T>): Spec<T> {
    return if (specs.size == 1) specs.first()
    else specs.first() and every(*specs.copyOfRange(1, specs.size))
}

fun <T> any(vararg specs: Spec<T>): Spec<T> {
    return if (specs.size == 1) specs.first()
    else specs.first() or any(*specs.copyOfRange(1, specs.size))
}

infix fun <T> Spec<T>.and(right: Spec<T>): Spec<T> = AndSpec(this, right)
infix fun <T> Spec<T>.or(right: Spec<T>): Spec<T> = OrSpec(this, right)
operator fun <T> Spec<T>.not(): Spec<T> = NotSpec(this)

infix fun <T> Spec<T>.isSatisfyThat(candidate: T): Boolean = this.isSatisfy(candidate)
infix fun <T> T.isSatisfyThat(spec: Spec<T>): Boolean = spec.isSatisfy(this)
infix fun <T> T.isSatisfyThat(spec: () -> Spec<T>): Boolean = spec().isSatisfy(this)

inline fun <reified T> eq(right: T): Spec<T> = spec { left -> left == right }
inline fun <reified T : Comparable<T>> lt(right: T): Spec<T> = spec { left -> left < right }
inline fun <reified T : Comparable<T>> lte(right: T): Spec<T> = !gt(right)
inline fun <reified T : Comparable<T>> gt(right: T): Spec<T> = spec { left -> left > right }
inline fun <reified T : Comparable<T>> gte(right: T): Spec<T> = !lt(right)
inline fun <reified T : Comparable<T>> rangeOf(start: T, endInclusive: T): Spec<T> = rangeOf(start.rangeTo(endInclusive))
inline fun <reified T : Comparable<T>> rangeOf(range: ClosedRange<T>): Spec<T> = spec { left -> range.contains(left) }
inline fun <reified T> includedTo(vararg item: T): Spec<T> = spec { left -> item.contains(left) }

