package com.example.taskbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskBackendApplication

fun main(args: Array<String>) {
    runApplication<TaskBackendApplication>(*args)
}
