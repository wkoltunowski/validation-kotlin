package com.falco.workshop.validation

class DuplicateCodeValidator : RowValidator {
    override fun validate(rows: List<Row>) {

    }

    fun Row.readCode() = readAs<String>("code")
}
