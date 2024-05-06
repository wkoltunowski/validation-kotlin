package com.falco.workshop.validation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.function.Consumer

class OrgUnitValidatorTest {
    @Test
    fun shouldDetectDuplicates() {
        val rows = createRows(
            row(code("C1")),
            row(code("C1")),
            row(code("C2")),
            row(code("C3"))
        )

        rows.validate()

        assertThat(rows[0].validationResults()).containsOnly("msg.duplicate.code")
        assertThat(rows[1].validationResults()).containsOnly("msg.duplicate.code")
        assertThat(rows[2].validationResults()).isEmpty()
        assertThat(rows[3].validationResults()).isEmpty()
    }

    @Test
    fun shouldDetectOverlaps() {
        val rows = createRows(
            row(from("2018-01-01"), to("2018-01-31")),
            row(from("2018-01-10"), to("2018-01-15")),
            row(from("2018-02-01"), to("2018-02-20")),
        )

        rows.validate()

        assertThat(rows[0].validationResults()).containsOnly("msg.overlapping.code")
        assertThat(rows[1].validationResults()).containsOnly("msg.overlapping.code")
        assertThat(rows[2].validationResults()).isEmpty()
    }

    private fun createRows(vararg rows: Row): List<Row> = rows.toList()

    private fun List<Row>.validate() {
        OrgUnitValidator().validate(this)
    }

    private fun from(from: String): Consumer<MutableMap<String, Any?>> =
        Consumer { it["from"] = date(from) }

    private fun to(to: String?): Consumer<MutableMap<String, Any?>> =
        Consumer { it["to"] = to?.let { date(it) } }

    private fun code(value: String?): Consumer<MutableMap<String, Any?>> =
        Consumer { it["code"] = value }

    private fun date(day: String): LocalDate = LocalDate.parse(day)

    private fun row(vararg consumers: Consumer<MutableMap<String, Any?>>): Row {
        val attributesMap = mutableMapOf<String, Any?>()
        consumers.forEach { it.accept(attributesMap) }
        return Row(attributesMap)
    }
}