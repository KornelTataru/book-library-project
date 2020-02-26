package com.booklibrary.libraryservice.exception

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import javax.validation.ConstraintViolation

@JsonInclude(NON_NULL)
class ApiError(val status: Int? = null, val message: String? = null,
               val details: String? = null, val debugMessage: String? = null) {

    var errors: List<FieldValidationError>? = null

    private fun addSubError(subError: FieldValidationError) {
        errors = listOf(subError)
    }

    private fun addValidationErrors(obj: String? = null, field: String? = null,
                                    rejectedValue: Any? = null, message: String? = null) {
        addSubError(FieldValidationError(obj, field, rejectedValue, message))
    }

    private fun addValidationErrors(fieldError: FieldError) {
        this.addValidationErrors(
                fieldError.objectName,
                fieldError.field,
                fieldError.rejectedValue,
                fieldError.defaultMessage)
    }

    fun addValidationFieldErrors(fieldErrors: List<FieldError>) {
        fieldErrors.forEach(this::addValidationErrors)
    }

    private fun addValidationErrors(objectError: ObjectError) {
        this.addValidationErrors(
                obj = objectError.objectName,
                message = objectError.defaultMessage)
    }

    fun addValidationGlobalErrors(globalErrors: List<ObjectError>) {
        globalErrors.forEach(this::addValidationErrors)
    }

    private fun addValidationErrors(cv: ConstraintViolation<*>) {
        this.addValidationErrors(
                cv.rootBeanClass.simpleName,
                cv.propertyPath.toString(),
                cv.invalidValue,
                cv.message)
    }

    fun addValidationErrors(constraintViolations: Set<ConstraintViolation<*>>) {
        constraintViolations.forEach(this::addValidationErrors)
    }
}
