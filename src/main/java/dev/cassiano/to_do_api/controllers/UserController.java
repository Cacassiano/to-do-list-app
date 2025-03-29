package dev.cassiano.to_do_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cassiano.to_do_api.entitys.User;
import dev.cassiano.to_do_api.repositorys.UserRepository;

@RestController
@RequestMapping("/")

public class UserController {
    
    @Autowired
    private UserRepository repository;


    /* 
    @GetMapping("/{nome}/{email}")
    public String testEntity(@PathVariable("nome") String nome, @PathVariable("email") String email){
        User nUser = new User();
        nUser.setNome(nome);
        nUser.setEmail(email);
        nUser.setTarefas(0);
        try{
            repository.save(nUser);
            return repository.findAll().toString();
        }catch(Exception e)
        {
            return e.getMessage();
        }

    }
    */
}
