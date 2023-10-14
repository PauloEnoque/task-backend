package com.example.taskbackend.services

import com.example.taskbackend.models.Tasks
import com.example.taskbackend.repository.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class TaskService {

    @Autowired
    lateinit var taskRepository: TaskRepository

    fun getAllTasks(): Iterable<Tasks> {
        return taskRepository.findAll()
    }

    fun createTask(task: Tasks): Tasks {
        return taskRepository.save(task)
    }

    fun getById(id: Int): Optional<Tasks>{
        return taskRepository.findById(id)
    }

}