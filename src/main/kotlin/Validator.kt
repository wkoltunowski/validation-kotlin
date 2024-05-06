package com.falco.workshop.validation

interface RowValidator {
    fun validate(rows: List<Row>)
}

class Row(private val attributes: Map<String, Any?>) {
    private val validationResults: MutableSet<String> = mutableSetOf()

    fun validationResults(): Set<String> = validationResults

    fun addValidationMessages(validationMessages: Collection<String>) {
        validationResults += validationMessages
    }

    fun <T> readAs(property: String): T? = attributes[property] as T?
}