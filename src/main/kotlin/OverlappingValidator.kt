package com.falco.workshop.validation

import java.time.LocalDate
import java.time.LocalDate.MAX
import java.time.LocalDate.MIN

class OverlappingValidatorValidator : RowValidator {
    override fun validate(rows: List<Row>) {

    }
    fun Row.readFrom() = readAs<LocalDate>("from") ?: MIN
    fun Row.readTo() = readAs<LocalDate>("to") ?: MAX
}
