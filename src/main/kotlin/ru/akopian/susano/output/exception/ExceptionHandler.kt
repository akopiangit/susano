package ru.akopian.susano.output.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import kotlin.Exception


@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    fun baseExceptionHandler(ex: Exception, request: WebRequest?): String = ex.message ?: "Internal server error"

}