package dev.cassiano.to_do_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.cassiano.to_do_api.entities.Task;
import dev.cassiano.to_do_api.entities.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    Optional<Task> findByIdAndUser(Long id, User user);
}
