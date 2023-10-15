package com.example.taskbackend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class TaskBackendApplication : SpringBootServletInitializer(){
    override fun configure(builder: SpringApplicationBuilder): SpringApplicationBuilder {
        return builder.sources(TaskBackendApplication::class.java)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(TaskBackendApplication::class.java, *args)
        }
    }
}

