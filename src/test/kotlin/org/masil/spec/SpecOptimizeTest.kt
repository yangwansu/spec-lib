package org.masil.spec

import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Created by yangwansu on 2019-07-12.
 */
class SpecOptimizeTest {

    private lateinit var T: Spec<Any>
    private lateinit var F: Spec<Any>

    @BeforeEach
    internal fun setUp() {
        T = spy(alwaysTrue)
        F = spy(alwaysFalse)
    }

    @Test
    fun or1() {

        1 isSatisfyThat { any(T, F) }

        verify(T).isSatisfy(1)
        verifyZeroInteractions(F)
    }

    @Test
    fun or2() {

        1 isSatisfyThat { any(T, T) }

        verify(T, times(1)).isSatisfy(1)
    }

    @Test
    fun or3() {

        1 isSatisfyThat { any(F, F) }

        verify(F, times(2)).isSatisfy(1)
    }

    @Test
    fun or4() {

        1 isSatisfyThat { any(F, T) }

        verify(F).isSatisfy(1)
        verify(T).isSatisfy(1)
    }

    @Test
    fun and1() {

        1 isSatisfyThat { T and F }

        verify(T).isSatisfy(1)
        verify(F).isSatisfy(1)
    }

    @Test
    fun and2() {

        1 isSatisfyThat { every(T, T) }

        verify(T, times(2)).isSatisfy(1)
    }

    @Test
    fun and3() {

        1 isSatisfyThat { every(F, F) }

        verify(F).isSatisfy(1)
    }

    @Test
    fun and4() {

        1 isSatisfyThat { every(F, T) }

        verify(F).isSatisfy(1)
    }


}