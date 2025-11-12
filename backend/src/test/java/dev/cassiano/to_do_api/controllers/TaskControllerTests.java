package dev.cassiano.to_do_api.controllers;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.cassiano.to_do_api.entities.User;
import dev.cassiano.to_do_api.infra.security.TokenService;
import dev.cassiano.to_do_api.repositories.UserRepository;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class TaskControllerTests {
    @Autowired
    private MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    // Security dependencies
    @Autowired
    private TokenService tokenService;
    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    private String token;

    @BeforeAll
    void setup() {
        // Creating a token
        String password = encoder.encode("testpassword");
        User testUser = userRepository.save(new User("testUser@test.com", password, "testUser"));
        token = tokenService.createToken(testUser);
    }
}
