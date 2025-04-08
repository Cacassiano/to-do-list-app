package dev.cassiano.to_do_api.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.cassiano.to_do_api.users.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    User findByUsername(String username);
    boolean existsByUsernameAndSenha(String username, String senha);
    boolean existsByUsername(String username);
}
