package dev.cassiano.to_do_api.dtos.user;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginDTO {
    @NotBlank
    @Length(min = 8)
    private final String password;

    @Email
    @NotBlank
    private final String email;
}
