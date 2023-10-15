package com.example.taskbackend.controllers

import com.example.taskbackend.models.Tasks
import com.example.taskbackend.services.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/tasks")
class TasksController {

    @Autowired
    lateinit var taskService: TaskService

    @GetMapping
    fun getAll(): ResponseEntity<Iterable<Tasks>>{
        val tasks = taskService.getAllTasks()
        return ResponseEntity.ok(tasks)
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<Tasks>{
        val task = taskService.getById(id)

        return if (task.isEmpty)
            ResponseEntity.notFound().build()
        else
             ResponseEntity.ok(task.get())
    }

    @PostMapping
    fun addTask(@RequestBody task: Tasks, ucb: UriComponentsBuilder): ResponseEntity<Unit>{
        val newTask = Tasks(id = 0, name = task.name, description = task.description)
        val savedTask = taskService.createTask(newTask)

        val location = ucb.path("api/tasks/{id}")
            .buildAndExpand(savedTask.id)
            .toUri()

        return ResponseEntity
                    .created(location)
                    .build()
    }

    fun infinite(){
        while (true){

        }
    }
}