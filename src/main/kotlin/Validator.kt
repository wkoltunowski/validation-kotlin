package com.falco.workshop.validation

import java.time.LocalDate

interface RowValidator {
    fun validate(rows: List<Row>)
}

class Row(val code: String?, val from: LocalDate?, val to: LocalDate?) {

    private val validationMessages = mutableSetOf<String>()

    fun validationMessages(): Set<String> = validationMessages.toSet()

    fun addValidationMessage(message: String) {
        validationMessages += message
    }
}