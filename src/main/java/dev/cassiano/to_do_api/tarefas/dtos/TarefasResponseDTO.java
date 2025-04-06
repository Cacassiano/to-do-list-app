package dev.cassiano.to_do_api.tarefas.dtos;

import dev.cassiano.to_do_api.tarefas.Tarefa;

public record TarefasResponseDTO(String title,String descricao,String dono,boolean concluida, Long id, String criacao) {

    public TarefasResponseDTO(Tarefa tarefa) {
        this(tarefa.getTitle(), tarefa.getDescricao(), tarefa.getDono(), tarefa.getConcluida(), tarefa.getId(), tarefa.getCriacao());
    }
    
}
