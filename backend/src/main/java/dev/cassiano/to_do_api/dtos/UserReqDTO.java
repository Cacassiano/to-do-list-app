package dev.cassiano.to_do_api.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class UserReqDTO {
    @Email(message = "not a valid email")
    @NotBlank(message = "email cant be blank")
    private final String email;

    @NotBlank(message = "username cant be blank")
    private final String username;

    @NotBlank(message = "password cant be blank")
    @Length(min = 8, message = "invalid password")
    private final String password;
}
