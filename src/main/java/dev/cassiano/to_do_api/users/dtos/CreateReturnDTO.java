package dev.cassiano.to_do_api.users.dtos;

import dev.cassiano.to_do_api.users.User;

public record CreateReturnDTO(String id) {
    public CreateReturnDTO(User user) {
        this(user.getId());
    }
}
