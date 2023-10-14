package com.example.taskbackend.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "task")
data class Tasks(@Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Int,
                 var name: String,
                 var description: String)
