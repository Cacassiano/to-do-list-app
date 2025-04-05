package dev.cassiano.to_do_api.SpringSecurity.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cassiano.to_do_api.SpringSecurity.security.dto.TokenDTO;
import dev.cassiano.to_do_api.SpringSecurity.security.services.TokenService;
import dev.cassiano.to_do_api.users.User;

import org.springframework.security.core.Authentication;



@RestController
@RequestMapping("/validation")
public class TokenController {

    @Autowired
    AuthenticationManager manager;

    @Autowired
    private TokenService service;
    

    @PostMapping("/createToken")
    public String generateToken(@RequestBody TokenDTO user) throws Exception
    {
        /* Cria o token de identificação */
        UsernamePasswordAuthenticationToken password = new UsernamePasswordAuthenticationToken(user.username(),user.senha());
        /* Valida esse token criado */
        Authentication validation = this.manager.authenticate(password);
        String token = service.generateToken((User) validation.getPrincipal());

        return token;
    }        
}
