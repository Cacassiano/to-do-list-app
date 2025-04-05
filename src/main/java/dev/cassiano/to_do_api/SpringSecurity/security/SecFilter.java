package dev.cassiano.to_do_api.SpringSecurity.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.cassiano.to_do_api.SpringSecurity.security.services.TokenService;
import dev.cassiano.to_do_api.users.User;
import dev.cassiano.to_do_api.users.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecFilter extends OncePerRequestFilter{

    @Autowired
    UserRepository repository;

    @Autowired
    TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        String token = this.recoverToken(request);
        if(token != null){
            try {
                String username = tokenService.userValidation(token);    
                User user = repository.findByUsername(username);

                UsernamePasswordAuthenticationToken authorization = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authorization);

            } catch (Exception e) {
                throw new ServletException(e.getMessage());
            }     
        } else {
            filterChain.doFilter(request, response);
        }

    }
        
            private String recoverToken(HttpServletRequest request) {
                String header = request.getHeader("Authorization");
                if(header == null){return null;}
               return header.replace("Bearer ", "");
            }
    
}
