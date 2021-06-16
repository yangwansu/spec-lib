package org.masil.spec

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


/**
 * Created by yangwansu on 2019-07-04.
 */
class OperatorsForLogicKtTest {


    @Test
    fun `operation of Logic`() {

        val even = Spec.create<Int> { i -> i % 2 == 0 }
        val multipleOfThree = Spec.create<Int> { i -> i % 3 == 0 }


        assertFalse { 1 isSatisfyThat even }
        assertTrue { 1 isSatisfyThat !even }

        assertFalse(8 isSatisfyThat { even and gte(10) })
        assertFalse(9 isSatisfyThat { even and gte(10) })
        assertTrue(10 isSatisfyThat { even and gte(10) })
        assertFalse(11 isSatisfyThat { even and gte(10) })
        assertTrue(12 isSatisfyThat { even and gte(10) })

        assertFalse { 1 isSatisfyThat { even and gte(10) and multipleOfThree } }
        assertTrue { 3 isSatisfyThat { even and gte(10) or multipleOfThree } }
        assertTrue { 3 isSatisfyThat { (even and gte(10)) or multipleOfThree } }

        assertFalse { 2 isSatisfyThat { even or gte(10) and multipleOfThree } }
        assertFalse { 10 isSatisfyThat { even or gte(10) and multipleOfThree } }
        assertFalse { 3 isSatisfyThat { even or gte(10) and multipleOfThree } }

        assertTrue { 12 isSatisfyThat { (even or gte(10)) and multipleOfThree } }
        assertTrue { 15 isSatisfyThat { (even or gte(10)) and multipleOfThree } }

    }

    @Test
    fun allOrAny() {

        assertTrue { 1 isSatisfyThat every(alwaysTrue, alwaysTrue, alwaysTrue) }
        assertFalse { 1 isSatisfyThat every(alwaysFalse, alwaysTrue, alwaysTrue) }

        assertTrue { 1 isSatisfyThat any(alwaysTrue, alwaysTrue, alwaysTrue) }
        assertTrue { 1 isSatisfyThat any(alwaysFalse, alwaysTrue, alwaysTrue) }
    }
}