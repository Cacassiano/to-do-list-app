package dev.cassiano.to_do_api.DTOs;

public record UserReqDTO(Long id , String nome, int tarefas, String email) {
}
