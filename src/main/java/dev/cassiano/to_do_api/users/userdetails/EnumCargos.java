package dev.cassiano.to_do_api.users.userdetails;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumCargos {
    ADM("admin"),

    USUARIO("user");

    private String cargo;
    

}
