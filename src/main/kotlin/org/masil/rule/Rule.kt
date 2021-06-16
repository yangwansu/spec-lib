package org.masil.rule

import org.masil.fact.Fact
import org.masil.spec.Spec
import org.masil.spec.isSatisfyThat

class Rule<T>(
    private val leftFactName: String,
    private val leftFactClass: Class<T>,
    private val spec: Spec<T>
) : Spec<Fact> {
    override fun isSatisfy(candidate: Fact): Boolean {

        val value = (candidate[leftFactName]?:throw IllegalArgumentException("did not find property of '$leftFactName' in fact"))
        try {
            val left = leftFactClass.cast(value)
            return left isSatisfyThat spec
        } catch (e:ClassCastException) {
            throw IllegalArgumentException("could not cast property of '$leftFactName' to '${leftFactClass.simpleName}' in fact")
        }
    }
}

inline infix fun <reified T> String.eq(right: T): Rule<T> = Rule(this, T::class.java, org.masil.spec.eq(right))
inline infix fun <reified T : Comparable<T>> String.lt(right: T): Rule<T> = Rule(
    this,
    T::class.java,
    org.masil.spec.lt(right)
)
inline infix fun <reified T : Comparable<T>> String.gt(right: T): Rule<T> = Rule(
    this,
    T::class.java,
    org.masil.spec.gt(right)
)
inline infix fun <reified T : Comparable<T>> String.lte(right: T): Rule<T> = Rule(
    this,
    T::class.java,
    org.masil.spec.lte(right)
)
inline infix fun <reified T : Comparable<T>> String.gte(right: T): Rule<T> = Rule(
    this,
    T::class.java,
    org.masil.spec.gte(right)
)
inline infix fun <reified T : Comparable<T>> String.rangeOf(range: Pair<T,T>): Rule<T> = this.rangeOf(range.first.rangeTo(range.second))
inline infix fun <reified T : Comparable<T>> String.rangeOf(range: ClosedRange<T>): Rule<T> = Rule(
    this,
    T::class.java,
    org.masil.spec.rangeOf(range)
)
inline infix fun <reified T> String.includedTo(items: Array<T>): Rule<T> = Rule(
    this,
    T::class.java,
    org.masil.spec.includedTo(*items)
)