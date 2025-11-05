package dev.cassiano.to_do_api.dtos.user;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class UserReqDTO {
    @NotBlank(message = "Username cant be blank")
    private final String username;

    @Length(min = 8, message = "Password must have a minimum length of 8 characters")
    private final String password;

    @Email(message = "The given email is not valid")
    @NotBlank(message = "Email cant be blank or null")
    private final String email;
}
