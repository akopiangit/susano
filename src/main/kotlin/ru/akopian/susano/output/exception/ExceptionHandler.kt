package ru.akopian.susano.output.exception

import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import kotlin.Exception


@RestControllerAdvice
//TODO переписать на webflux
class ControllerExceptionHandler {
    companion object : KLogging()

    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    fun baseExceptionHandler(ex: Exception): String {
        logger.error(ex) { ex.message }
        return ex.message ?: "Internal server error"
    }

}