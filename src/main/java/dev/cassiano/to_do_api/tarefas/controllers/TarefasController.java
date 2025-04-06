package dev.cassiano.to_do_api.tarefas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cassiano.to_do_api.SpringSecurity.security.services.TokenService;
import dev.cassiano.to_do_api.tarefas.dtos.TarefasDTO;
import dev.cassiano.to_do_api.tarefas.dtos.TarefasResponseDTO;
import dev.cassiano.to_do_api.tarefas.service.TarefasService;
import dev.cassiano.to_do_api.users.User;
import dev.cassiano.to_do_api.users.repository.UserRepository;

@RestController
@RequestMapping("/tarefas")
public class TarefasController {
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TarefasService service;

    private User getUserByHeader(String header) throws Exception {
        return repository.findByUsername(tokenService.userValidation(header.replace("Bearer ", "")));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTarefa(@RequestBody TarefasDTO body, @RequestHeader("Authorization") String header) throws Exception {
        if (( body.title() != null & body != null ) & header != null){
            User user = getUserByHeader(header);
            return service.createTarefa(user.getUsername(), body);
        }
        return ResponseEntity.unprocessableEntity().body("requisicão invalida!");
        
                
    }

    @GetMapping("/getAll")
    public List<TarefasResponseDTO> getAllTarefas(@RequestHeader("Authorization") String header) throws Exception {
        User user = getUserByHeader(header);
        return service.getAllTarefas(user.getUsername());
    }

}
