package dev.cassiano.to_do_api.users.userdetails;


import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserHierarqy {
    public List<SimpleGrantedAuthority> grantAutority(String cargo)
    {
        if(cargo == EnumCargos.ADM.getCargo())
        {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));

    }
}
