package org.masil.spec

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalDateTime.MAX
import java.time.LocalDateTime.MIN
import java.time.LocalTime
import java.util.stream.Stream


/**
 * Created by yangwansu on 2019-07-12.
 */
class ComparableRightTest {

    companion object {
        @JvmStatic
        private fun provideComparable(): Stream<Arguments> {
            return Stream.of(
                args('1', '0'),
                args(1L, 0L),
                args(1f, 0f),
                args(1.0, 0.0),
                args(LocalDateTime.now(), LocalDateTime.now().minusSeconds(1)),
                args(LocalDate.now(), LocalDate.now().minusDays(1)),
                args(LocalTime.now(), LocalTime.now().minusSeconds(1))
            )
        }

        private fun args(vararg obj: Any) = Arguments.of(*obj)
    }

    @ParameterizedTest
    @MethodSource("provideComparable")
    inline fun <reified T : Comparable<T>> greaterThanEquals(left: T, right: T) {
        assertTrue { left isSatisfyThat gte(right) }
        assertFalse { left isSatisfyThat lte(right) }
    }

    @Test
    fun rangeOf() {
        assertTrue { 1 isSatisfyThat rangeOf(0, 10) }
        assertTrue { 10 isSatisfyThat rangeOf(0, 10) }
        assertFalse { 11 isSatisfyThat rangeOf(0, 10) }


        assertTrue { LocalDateTime.now() isSatisfyThat rangeOf(MIN, MAX) }
    }

    @Test
    fun includedTo() {
        assertTrue { 1 isSatisfyThat includedTo(0, 1, 2) }
        assertFalse { 3 isSatisfyThat includedTo(0, 1, 2) }

    }

    @Test
    fun eq() {

        assertTrue { 1 isSatisfyThat eq(1) }
        assertFalse { 1 isSatisfyThat eq(0) }

        class A
        assertFalse { A() isSatisfyThat eq(A()) }

        data class B(val v:Any)
        assertTrue { B(1) isSatisfyThat eq(B(1)) }
        assertFalse { B(1) isSatisfyThat eq(B(2)) }

    }
}

