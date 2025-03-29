package dev.cassiano.to_do_api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.cassiano.to_do_api.entitys.Tarefa;

public interface TarefasRepository extends JpaRepository<Tarefa, Long>{}
