package dev.cassiano.to_do_api.users;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dev.cassiano.to_do_api.users.dtos.UserReqDTO;
import dev.cassiano.to_do_api.users.userdetails.UserHierarqy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users")

public class User implements UserDetails{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(unique = true)
    Long id;
    
    @Column(unique = true)
    String username;

    String email;
    
    String senha;

    String cargo;

    public User(UserReqDTO req)
    {
        this.email = req.email();
        this.username = req.username();
        this.senha = req.senha();
        this.cargo = req.cargo();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new UserHierarqy().grantAutority(this.cargo);
    }

    @Override
    public String getPassword() {
        return this.senha;
    }
}
