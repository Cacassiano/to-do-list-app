package dev.cassiano.to_do_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cassiano.to_do_api.dtos.TaskReqDTO;
import dev.cassiano.to_do_api.dtos.TaskResDTO;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @PostMapping
    public ResponseEntity<TaskResDTO> createTask(@RequestBody TaskReqDTO req) {
        return ResponseEntity.notFound().build();
    }
}
