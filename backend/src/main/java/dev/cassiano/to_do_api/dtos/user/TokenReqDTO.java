package dev.cassiano.to_do_api.dtos.user;

import lombok.Getter;

@Getter
public class TokenReqDTO {
    private final String token;
    public TokenReqDTO(String header) {
        this.token = header.replace("Bearer ", "");
    }
}
