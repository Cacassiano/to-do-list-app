package dev.cassiano.to_do_api.tarefas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cassiano.to_do_api.tarefas.dtos.TarefasDTO;
import dev.cassiano.to_do_api.tarefas.service.TarefasService;
import dev.cassiano.to_do_api.users.repository.UserRepository;

@RestController
@RequestMapping("/tarefas")
public class TarefasController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TarefasService service;


    @PostMapping("/create")
    public ResponseEntity<String> createATask(@RequestBody TarefasDTO req) {
        return ResponseEntity.ok().build();
    }

}
