package dev.cassiano.to_do_api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.cassiano.to_do_api.entitys.Tarefa;

@Repository
public interface TarefasRepository extends JpaRepository<Tarefa, Long>{}
