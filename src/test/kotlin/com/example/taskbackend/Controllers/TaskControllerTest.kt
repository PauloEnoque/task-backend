package com.example.taskbackend.Controllers


import com.example.taskbackend.models.Tasks
import com.example.taskbackend.services.TaskService
import com.jayway.jsonpath.JsonPath
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @MockBean
    lateinit var taskService: TaskService

    lateinit var task: Tasks

    @BeforeEach
    fun setUp(){
        task = Tasks(
            id = 999,
            name = "Test Task",
            description = "Just a new task"
        )
    }

    @Test
    fun shouldReturnTaskWhenValid(){
        // Mocking the getById method of the service
        `when`(taskService.getById(1))
            .thenReturn(
                Optional.of(Tasks(
                    id = 1,
                    name = "First Task",
                    description = "Just Another Task")))

        val response = restTemplate.getForEntity("/api/tasks/1", String::class.java)

        assertThat(response.statusCode)
            .isEqualTo(HttpStatus.OK)

        val documentContext = JsonPath.parse(response.body)

        val id = documentContext.read<Int>("$.id")
        assertThat(id).isEqualTo(1)

        val name = documentContext.read<String>("$.name")
        assertThat(name).isEqualTo("First Task")

    }

    @Test
    fun shouldReturnNotFoundWhenIdIsInvalid(){
        // Mocking the getById method of the service
        `when`(taskService.getById(3))
            .thenReturn(
                Optional.empty())

         val response = restTemplate.getForEntity("/api/tasks/3", String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(response.body).isBlank()

    }

    @Test
    fun shouldCreateNewTask(){
        val newTask = Tasks(id = 0, name = task.name, description = task.description)
        `when`(taskService.createTask(task = newTask)).thenReturn(newTask)
        `when`(taskService.getById(0)).thenReturn(Optional.of(newTask))

        val response = restTemplate.postForEntity("/api/tasks", task, String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        println(response.headers.location)

        val URI = response.headers.location

        val responseFromGet = restTemplate.getForEntity(URI, String::class.java)
        assertThat(responseFromGet.statusCode).isEqualTo(HttpStatus.OK)

        val docContext = JsonPath.parse(responseFromGet.body)
        assertThat(0).isEqualTo(docContext.read("$.id"))
        assertThat("Test Task").isEqualTo(docContext.read("$.name"))

    }



}