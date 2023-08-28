package ru.akopian.susano

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SusanoApplication

fun main(args: Array<String>) {
    runApplication<SusanoApplication>(*args)
}
