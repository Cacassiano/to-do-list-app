package dev.cassiano.to_do_api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.cassiano.to_do_api.entitys.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByNomeAndEmail(String nome, String email);
    boolean existsByNomeAndEmail(String nome, String email);
}
