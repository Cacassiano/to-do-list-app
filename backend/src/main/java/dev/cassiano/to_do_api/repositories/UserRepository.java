package dev.cassiano.to_do_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.cassiano.to_do_api.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    
}
