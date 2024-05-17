package com.falco.workshop.validation

import DuplicateValidatorJava
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ValidatorsTest {
    @Test
    fun shouldDetectDuplicates() {
        val rows = listOf(
            row("C1"),
            row("C1"),
            row("C2"),
            row("C3")
        )

        DuplicateValidatorJava().validate(rows)

        assertThat(rows[0].validationMessages()).containsOnly("msg.duplicate.code")
        assertThat(rows[1].validationMessages()).containsOnly("msg.duplicate.code")
        assertThat(rows[2].validationMessages()).isEmpty()
        assertThat(rows[3].validationMessages()).isEmpty()
    }

    @Test
    fun shouldDetectOverlaps() {
        val rows = listOf(
            row(from = "2018-01-01", to = "2018-01-31"),
            row(from = "2018-01-10", to = "2018-01-15"),
            row(from = "2018-02-01", to = "2018-02-20"),
        )

        OverlappingValidatorValidator().validate(rows)

        assertThat(rows[0].validationMessages()).containsOnly("msg.overlapping")
        assertThat(rows[1].validationMessages()).containsOnly("msg.overlapping")
        assertThat(rows[2].validationMessages()).isEmpty()
    }

    private fun row(code: String? = null, from: String? = null, to: String? = null) =
        Row(code, from?.toLocalDate(), to?.toLocalDate())

    private fun String.toLocalDate() = LocalDate.parse(this)
}