package dev.cassiano.to_do_api.tarefas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.cassiano.to_do_api.tarefas.Tarefa;
import dev.cassiano.to_do_api.tarefas.dtos.TarefasDTO;
import dev.cassiano.to_do_api.tarefas.dtos.TarefasResponseDTO;
import dev.cassiano.to_do_api.tarefas.repository.TarefasRepository;

@Service
public class TarefasService {
    
    @Autowired
    private TarefasRepository repository;

	public ResponseEntity<String> createTarefa(String dono_id, TarefasDTO req) {
        if(repository.findByTitleAndDono(req.title(), dono_id) == null) {

            repository.save(new Tarefa(req, dono_id));
            return ResponseEntity.status(HttpStatus.CREATED).body("Tarefa criada com sucesso");
        }
        return ResponseEntity.internalServerError().body("Já existe uma tarefa com este nome");
    }

    public List<TarefasResponseDTO> getAllTarefas(String username) {
        return repository.findAllByDono(username);
    }
    

}