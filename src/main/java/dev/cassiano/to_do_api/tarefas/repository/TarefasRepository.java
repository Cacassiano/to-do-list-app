package dev.cassiano.to_do_api.tarefas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.cassiano.to_do_api.tarefas.Tarefa;
import dev.cassiano.to_do_api.tarefas.dtos.TarefasResponseDTO;

@Repository
public interface TarefasRepository extends JpaRepository<Tarefa, String>
{
    Tarefa findByTitleAndDono(String title, String dono);
    List<TarefasResponseDTO> findAllByDono(String dono);

}
