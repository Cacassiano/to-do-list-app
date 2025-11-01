package dev.cassiano.to_do_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.cassiano.to_do_api.entities.User;
import dev.cassiano.to_do_api.infra.security.TokenService;
import dev.cassiano.to_do_api.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    public String saveUser(User user) {
        user = userRepository.save(user);
        return tokenService.createToken(user);
    }
}
