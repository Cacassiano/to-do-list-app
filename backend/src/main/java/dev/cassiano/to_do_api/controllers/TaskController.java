package dev.cassiano.to_do_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cassiano.to_do_api.dtos.TaskReqDTO;
import dev.cassiano.to_do_api.dtos.TaskResDTO;
import dev.cassiano.to_do_api.entities.Task;
import dev.cassiano.to_do_api.exceptions.customs.NotFoundException;
import dev.cassiano.to_do_api.services.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResDTO> createTask(
        @RequestBody TaskReqDTO req
        //@RequestHeader("Authorization") User user
    ) {
        Task task = taskService.createTask(req, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TaskResDTO(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResDTO> getTaksById(
        @PathVariable Long id 
        // @RequestHeader("Authorization") User user
    ) throws NotFoundException {
        Task task = taskService.getById(id);
        return ResponseEntity.ok(new TaskResDTO(task));
    }
}
