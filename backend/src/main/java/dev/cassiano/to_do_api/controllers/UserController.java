package dev.cassiano.to_do_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cassiano.to_do_api.dtos.TokenDTO;
import dev.cassiano.to_do_api.dtos.UserReqDTO;
import dev.cassiano.to_do_api.entities.User;
import dev.cassiano.to_do_api.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<TokenDTO> registerUser(@RequestBody @Valid UserReqDTO req) {
        String token = userService.saveUser(new User(req));
        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenDTO(token));
    }
}