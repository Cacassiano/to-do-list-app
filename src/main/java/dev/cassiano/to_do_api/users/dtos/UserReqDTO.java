package dev.cassiano.to_do_api.users.dtos;

public record UserReqDTO(String senha, String username, String email, String cargo) {
}
