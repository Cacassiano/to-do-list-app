package dev.cassiano.to_do_api.DTOs;


import dev.cassiano.to_do_api.entitys.User;



public record UserRespDTO(Long id , String nome, int tarefas, String email) {

    public UserRespDTO(User user)
    {
        this(user.getId(), user.getNome(), user.getTarefas(), user.getEmail());
    }
}
