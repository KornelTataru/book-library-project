package com.booklibrary.libraryservice.exception

import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestController
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    @Override
    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException,
                                              headers: HttpHeaders, status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(status.value(), "Malformed JSon request", "Validation failed", ex.localizedMessage)
        return ResponseEntity(apiError, status)
    }

    @Override
    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException,
                                              headers: HttpHeaders, status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(status.value(), "Validation error")
        apiError.addValidationFieldErrors(ex.bindingResult.fieldErrors)
        apiError.addValidationGlobalErrors(ex.bindingResult.globalErrors)

        return ResponseEntity(apiError, BAD_REQUEST)
    }

    @Override
    override fun handleMissingServletRequestParameter(ex: MissingServletRequestParameterException, headers: HttpHeaders,
                                                      status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val error = "${ex.parameterName} parameter is missing"
        val apiError = ApiError(status.value(), error, debugMessage = ex.localizedMessage)
        return ResponseEntity(apiError, BAD_REQUEST)
    }

    @Override
    override fun handleHttpMediaTypeNotSupported(ex: HttpMediaTypeNotSupportedException, headers: HttpHeaders,
                                                 status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val error = ex.localizedMessage
        val supportedType = "Supported content type: application/json"
        val apiError = ApiError(status.value(), error, supportedType)
        return ResponseEntity(apiError, UNSUPPORTED_MEDIA_TYPE)
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException::class)
    fun handleConstraintViolation(ex: javax.validation.ConstraintViolationException): ResponseEntity<Any> {
        val apiError = ApiError(BAD_REQUEST.value(), "Validation error")
        apiError.addValidationErrors(ex.constraintViolations)
        return ResponseEntity(apiError, BAD_REQUEST)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun handleEntityNotFound(ex: EntityNotFoundException, request: WebRequest): ResponseEntity<ApiError> {
        val apiError = ApiError(NOT_FOUND.value(), ex.message)
        return ResponseEntity(apiError, NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(ex: MethodArgumentTypeMismatchException, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(BAD_REQUEST.value(),
                "The parameter ${ex.name} of value ${ex.value} could not be converted to ${ex.requiredType?.simpleName}", ex.localizedMessage)
        return ResponseEntity(apiError, BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleNotHandledException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(INTERNAL_SERVER_ERROR.value(), "Something goes wrong", "Validation failed", ex.localizedMessage)

        return ResponseEntity(apiError, INTERNAL_SERVER_ERROR)
    }
}