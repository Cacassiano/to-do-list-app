package dev.cassiano.to_do_api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cassiano.to_do_api.dtos.task.TaskReqDTO;
import dev.cassiano.to_do_api.dtos.task.TaskResDTO;
import dev.cassiano.to_do_api.dtos.user.TokenReqDTO;
import dev.cassiano.to_do_api.entities.Task;
import dev.cassiano.to_do_api.entities.User;
import dev.cassiano.to_do_api.exceptions.customs.NotFoundException;
import dev.cassiano.to_do_api.services.TaskService;
import dev.cassiano.to_do_api.services.UserService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<TaskResDTO> createTask(
        @RequestBody TaskReqDTO req,
        @RequestHeader("Authorization") TokenReqDTO token
    ) throws NotFoundException{
        User user = userService.getUserByToken(token.getToken());
        Task task = taskService.saveTask(new Task(req, user));
        return ResponseEntity.status(HttpStatus.CREATED).body(new TaskResDTO(task));
    }

    @GetMapping
    public ResponseEntity<Map<String, List<TaskResDTO>>> getAll(
        @RequestHeader("Authorization") TokenReqDTO token
    ) throws NotFoundException {
        User user = userService.getUserByToken(token.getToken());
        Map<String, List<TaskResDTO>> response = new HashMap<>();
        response.put("data", user.getTasks().stream().map(TaskResDTO::new).toList());
        return ResponseEntity.ok(response);
    } 

    @GetMapping("/{id}")
    public ResponseEntity<TaskResDTO> getTaksById(
        @PathVariable Long id,
        @RequestHeader("Authorization") TokenReqDTO token
    ) throws NotFoundException {
        String user_email = userService.getEmailByToken(token.getToken());
        Task task = taskService.getById(id, user_email);
        return ResponseEntity.ok(new TaskResDTO(task));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
        @PathVariable Long id,  
        @RequestHeader("Authorization") TokenReqDTO token
    )throws NotFoundException {
        String user_email = userService.getEmailByToken(token.getToken());
        taskService.deleteById(id, user_email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResDTO> updateTask(
        @RequestBody TaskReqDTO req,
        @PathVariable Long id,
        @RequestHeader("Authorization") TokenReqDTO token
    )throws NotFoundException {
        String user_email = userService.getEmailByToken(token.getToken());
        Task task = taskService.getById(id, user_email);
        task.update(req);
        task = taskService.saveTask(task);
        return ResponseEntity.ok().body(new TaskResDTO(task));
    }
}