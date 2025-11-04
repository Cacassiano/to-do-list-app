package dev.cassiano.to_do_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.cassiano.to_do_api.entities.User;
import dev.cassiano.to_do_api.exceptions.customs.NotFoundException;
import dev.cassiano.to_do_api.infra.security.TokenService;
import dev.cassiano.to_do_api.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user = userRepository.save(user);

        return tokenService.createToken(user);
    }

    public String login(String email, String password) throws NotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        // Se senhas forem diferentes
        if(!passwordEncoder.matches(password,user.getPassword())) throw new RuntimeException();
        return tokenService.createToken(user);
    }

    public User getUserByToken(String token) throws NotFoundException{
        String email = tokenService.getEmailFromToken(token);
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
