package dev.cassiano.to_do_api.tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.cassiano.to_do_api.tarefas.Tarefa;

@Repository
public interface TarefasRepository extends JpaRepository<Tarefa, Long>{}
