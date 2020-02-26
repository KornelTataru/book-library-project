package com.booklibrary.libraryservice.exception

data class FieldValidationError(
        var obj: String?,
        var field: String?,
        var rejectedValue: Any?,
        var message: String?)