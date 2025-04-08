package dev.cassiano.to_do_api.tarefas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.cassiano.to_do_api.tarefas.Tarefa;
import dev.cassiano.to_do_api.tarefas.dtos.IdDTO;
import dev.cassiano.to_do_api.tarefas.dtos.TarefasDTO;
import dev.cassiano.to_do_api.tarefas.dtos.TarefasResponseDTO;
import dev.cassiano.to_do_api.tarefas.repository.TarefasRepository;
import dev.cassiano.to_do_api.users.repository.UserRepository;

@Service
public class TarefasService {
    
    @Autowired
    private TarefasRepository repository;
    @Autowired
    private UserRepository userRepository;

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

    public ResponseEntity<String> deleteTask(String dono_id, String title) {
        Tarefa tarefa = repository.findByTitleAndDono(title, dono_id);
        if(tarefa != null) {
            repository.delete(tarefa);
            return ResponseEntity.ok().body("tarefa deletada");
        }
        return ResponseEntity.internalServerError().body("Tarefa não existe");
    }

    public ResponseEntity<String> updateTask(String dono_id, TarefasDTO req) {
        Optional<Tarefa> nTarefa = repository.findById(req.id());
        if(!nTarefa.isEmpty()) {
            nTarefa.get().setTitle(req.title());
            nTarefa.get().setDono(dono_id);
            nTarefa.get().setConcluida(req.concluida());
            nTarefa.get().setDescricao(req.descricao());
            repository.save(nTarefa.get());
            return ResponseEntity.ok().body("tarefa atualizada");
        } 
        return ResponseEntity.internalServerError().body("Tarefa ou usuario não existem");
        
    }

    public ResponseEntity<IdDTO> getTaskId(String title, String dono_id) {
        Tarefa tarefa = repository.findByTitleAndDono(title, dono_id);
        if ( tarefa != null) {
            return ResponseEntity.ok().body(new IdDTO(tarefa.getId()));
        }
        return ResponseEntity.internalServerError().build();
    }

    public List<TarefasResponseDTO> getAll(String dono_id) throws Exception {
        if(userRepository.existsById(dono_id)) {
            return repository.findAllByDono(dono_id);
        }
        throw new Exception("Tarefa não existe");
    }
    
    

}