package org.masil.rule

import org.masil.fact.factOf
import org.masil.spec.and
import org.masil.spec.isSatisfyThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

/**
 * Created by yangwansu on 2019-07-12.
 */
class RuleTest {


    @Test
    fun `did not find property of '{leftFactName}' in fact`() {

        val exception = assertThrows<IllegalArgumentException> {
            factOf() isSatisfyThat { "score" gt 80 }
        }

        assertEquals("did not find property of 'score' in fact", exception.message)

        val exception2 = assertThrows<IllegalArgumentException> {
            factOf("score" to null) isSatisfyThat { "score" gt 80 }
        }

        assertEquals("did not find property of 'score' in fact", exception2.message)

    }

    @Test
    fun `could not cast property of '{leftFactName}' to '{type}' in fact`() {
        val exception = assertThrows<IllegalArgumentException> {
            factOf("score" to 'A') isSatisfyThat { "score" gt 80 }
        }

        assertEquals("could not cast property of 'score' to 'Integer' in fact", exception.message)

    }

    @Test
    fun ruleWithFact() {

        val fact = factOf(
            "location" to "seoul",
            "date" to LocalDate.parse("2019-01-10"),
            "temperature" to 5.0
        )

        assertTrue { fact isSatisfyThat { ("location" eq "seoul") and ("temperature" eq 5.0) } }

        assertTrue { fact isSatisfyThat ("temperature" eq 5.0) }
        assertTrue { fact isSatisfyThat ("temperature" lt 6.0) }
        assertTrue { fact isSatisfyThat ("temperature" lte 6.0) }
        assertTrue { fact isSatisfyThat ("temperature" gt 4.0) }
        assertTrue { fact isSatisfyThat ("temperature" gte 4.0) }
        assertTrue { fact isSatisfyThat ("temperature" rangeOf (3.0 to 5.0)) }
        assertTrue { fact isSatisfyThat ("temperature" rangeOf (3.0..5.0)) }
        assertTrue { fact isSatisfyThat ("temperature" includedTo (arrayOf(3.0, 4.0, 5.0, 6.0))) }

        assertFalse { fact isSatisfyThat ("temperature" eq 4.0) }
        assertFalse { fact isSatisfyThat ("temperature" lt 4.0) }
        assertFalse { fact isSatisfyThat ("temperature" lte 4.0) }
        assertFalse { fact isSatisfyThat ("temperature" gt 6.0) }
        assertFalse { fact isSatisfyThat ("temperature" gte 6.0) }
        assertFalse { fact isSatisfyThat ("temperature" rangeOf (3.0 to 4.0)) }
        assertFalse { fact isSatisfyThat ("temperature" rangeOf (3.0..4.0)) }
        assertFalse { fact isSatisfyThat ("temperature" includedTo (arrayOf(3.0, 4.0))) }


        assertFalse { (("temp" lt 10) and ("temp" lt 2)).isSatisfy(factOf("temp" to 5)) }

        assertTrue { factOf("temp" to 5) isSatisfyThat ("temp" lt 10) }

    }


}
