package dev.cassiano.to_do_api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.cassiano.to_do_api.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private  TokenService tokenService;
    @Autowired 
    private UserRepository userRepository;
    
    @Override
    @SuppressWarnings("null")
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
    ) throws ServletException, IOException {
        String token = this.getToken(request);
        System.out.println(token);
        if(token != null) {
            String email = tokenService.getEmailFromToken(token);
            userRepository.findByEmail(email).orElseThrow(() -> new IOException("User not exists"));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, null);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }



    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(header == null || header.isBlank()  || !header.startsWith("Bearer ") || header.length() < 8) return null;
        return header.replace("Bearer ", "");
    }
    
}
