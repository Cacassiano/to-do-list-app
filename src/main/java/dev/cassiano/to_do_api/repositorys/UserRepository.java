package dev.cassiano.to_do_api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cassiano.to_do_api.entitys.User;

public interface UserRepository extends JpaRepository<User, Long>{}
