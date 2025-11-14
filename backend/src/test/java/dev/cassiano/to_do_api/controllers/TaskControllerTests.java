package dev.cassiano.to_do_api.controllers;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import dev.cassiano.to_do_api.dtos.task.TaskReqDTO;
import dev.cassiano.to_do_api.dtos.task.TaskResDTO;
import dev.cassiano.to_do_api.entities.Task;
import dev.cassiano.to_do_api.entities.User;
import dev.cassiano.to_do_api.infra.security.TokenService;
import dev.cassiano.to_do_api.repositories.TaskRepository;
import dev.cassiano.to_do_api.repositories.UserRepository;
import dev.cassiano.to_do_api.services.enums.Status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class TaskControllerTests {
    @Autowired
    private MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String BASE_URL = "http://localhost:9000/api/tasks";
    // Security dependencies
    @Autowired
    private TokenService tokenService;
    private String token;
    @Autowired
    private PasswordEncoder encoder;
    // Repositories
    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository; 
    // User for tests
    private User testUser;
    // One Task for tasks
    Task testTask;

    @BeforeAll
    void setup() {
        // Setting mapper date time deserializer
        mapper.findAndRegisterModules();

        // Creating a token
        String password = encoder.encode("testpassword");
        testUser = userRepository.save(new User("testUser@test.com", password, "testUser"));
        token = tokenService.createToken(testUser);
        // Setting up a task to db
        this.testTask = new Task("TestTitle", "test Description", Status.PENDING, testUser);
        taskRepository.save(testTask);
    }  
    
    private String doAuthHeader() {
        return "Bearer " + this.token;
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Try get all tasks sucefully from db")
    void getAllCase1() throws UnsupportedEncodingException, Exception {
        // Add tasks to test
        Task getAllTasks;
        for(int i = 0; i< 3; i++) {
            getAllTasks = new Task("TestTitle", "test Description", Status.PENDING, testUser);
            taskRepository.save(getAllTasks);
        }

        // Perform the request and get the response as String
        String response = mvc.perform(
            get(this.BASE_URL)
            .header("Authorization", doAuthHeader())
        ).andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getContentAsString();
    

        Map<String, List<TaskResDTO>> responseAsMap = mapper.readValue(response, new TypeReference<Map<String, List<TaskResDTO>>>(){});
        List<TaskResDTO> tasks = responseAsMap.get("data");

        // Assert that will be 3 or more tasks because other test can create tasks 
        assertEquals(tasks.size()>=4, true);
    }

    @Test
    @DisplayName("Try get all tasks and Forbidden")
    void getAllCase2() throws UnsupportedEncodingException, Exception {
        mvc.perform(
            get(this.BASE_URL)
        ).andExpect(status().isForbidden());
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Try get task by id Succesfully")
    void getByIdCase1() throws Exception{
        String response = mvc.perform(
            get(this.BASE_URL+"/"+testTask.getId())
            .header("Authorization", doAuthHeader())
        ).andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getContentAsString();

        TaskResDTO resonseTask = mapper.readValue(response, TaskResDTO.class);

        assertEquals(resonseTask.id(), testTask.getId());
        assertEquals(resonseTask.title(), testTask.getTitle());
        assertEquals(resonseTask.description(), testTask.getDescription());
        assertEquals(resonseTask.status(), testTask.getStatus());
    }

    @Test
    @DisplayName("Try get by id and NotFound")
    void getByIdCase2() throws Exception{
        mvc.perform(
            get(this.BASE_URL+"/"+-1)
            .header("Authorization", doAuthHeader())
        ).andExpect(status().isNotFound());
    }

    @SuppressWarnings("null")
    @Test 
    @DisplayName("Try create a task succesfully")
    void createTaskCase1() throws Exception{
        // Create request dto
        TaskReqDTO request = new TaskReqDTO("My new test task", "Just for testing funcionalyties", "completed");
        // Parse to string the previus object
        String requestString = mapper.writeValueAsString(request);

        // Perform the request and get the response as string
        String response = mvc.perform(
            post(this.BASE_URL)
            .header("Authorization", doAuthHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestString)
        ).andExpectAll(
            status().isCreated(),
            content().contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getContentAsString();

        // Parse reponse to a java record
        TaskResDTO responseTask = mapper.readValue(response, TaskResDTO.class);

        // Validate the sended request with the received reponse
        assertEquals(responseTask.title(), request.getTitle());
        assertEquals(responseTask.description(), request.getDescription());
        assertEquals(responseTask.status(), request.getStatus());

        // Validate if the task has been saved in db
        assertTrue(taskRepository.existsById(responseTask.id()));

    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Try create a task and BadRequest")
    void createTaskCase2() throws Exception{
        // Invalid Title
        TaskReqDTO request = new TaskReqDTO(null, null, "completed");
        // Parse to string
        String requestString = mapper.writeValueAsString(request);

        // Badrequest com o title
        mvc.perform(
            post(this.BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", doAuthHeader())
            .content(requestString)
        ).andExpect(status().isBadRequest());

        // Invalid status
        requestString = "{\"title\": \"test title\", \"status\": \"scheduled\"}";

        // Badrequest with the invalid status
        mvc.perform(
            post(this.BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", doAuthHeader())
            .content(requestString)
        ).andExpect(status().isBadRequest());
    }


    @Test
    @SuppressWarnings("null")
    @DisplayName("Try delete succesfully")
    void deleteByIdCase1() throws Exception {
        // Create a new task
        Task testDeleteTask = new Task("test title", "test description", Status.IN_PROGRESS, testUser);
        testDeleteTask = taskRepository.save(testDeleteTask);

        // Perform the delete task
        mvc.perform(
            delete( this.BASE_URL+"/"+testDeleteTask.getId() )
            .header("Authorization", doAuthHeader())
        ).andExpect( status().isNoContent() );

        // Verify if the task was deleted from db
        assertFalse(taskRepository.existsById(testDeleteTask.getId()));
    }

    @Test
    @SuppressWarnings("null")
    @DisplayName("Try delete and NotFound")
    void deleteByIdCase2() throws Exception {
        // Pass a invalid id
        mvc.perform(
            delete( this.BASE_URL+"/"+-1 )
            .header("Authorization", doAuthHeader())
        ).andExpect( status().isNotFound() );


        // Creating a new token 
        String password = encoder.encode("testpassword");
        User newUser = userRepository.save(new User("testUser2@test.com", password, "testUser"));
        String newToken = tokenService.createToken(newUser);

        // Try delete a task that is not the owner
        mvc.perform(
            delete(this.BASE_URL+"/"+testTask.getId())
            .header("Authorization", "Bearer "+newToken)
        ).andExpect(status().isNotFound());

        // Verify if the task was not deleted from db
        assertTrue(taskRepository.existsById(testTask.getId()));
    }

}