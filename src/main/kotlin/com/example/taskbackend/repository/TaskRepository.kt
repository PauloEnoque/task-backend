package com.example.taskbackend.repository

import com.example.taskbackend.models.Tasks
import org.springframework.data.repository.CrudRepository

interface TaskRepository : CrudRepository<Tasks, Int> {
}