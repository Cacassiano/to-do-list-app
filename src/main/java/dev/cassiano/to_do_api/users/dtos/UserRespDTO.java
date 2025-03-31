package dev.cassiano.to_do_api.users.dtos;


import dev.cassiano.to_do_api.users.User;



public record UserRespDTO(Long id , String nome, String email, String senha, String cargo) {

    public UserRespDTO(User user)
    {
        this(user.getId(), user.getUsername(), user.getEmail(), user.getSenha(), user.getCargo());
    }
}
