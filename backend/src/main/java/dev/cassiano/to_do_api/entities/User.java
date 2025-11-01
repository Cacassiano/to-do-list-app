package dev.cassiano.to_do_api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import dev.cassiano.to_do_api.dtos.UserReqDTO;

@Entity(name="users")
@Table(name="users")
@Getter
@NoArgsConstructor
public class User {

    public User(UserReqDTO req) {
        this.email = req.getEmail();
        this.password = req.getPassword();
        this.username = req.getUsername();
    }

    @GeneratedValue(strategy=GenerationType.UUID) @Column(name = "id", nullable=false, unique= true) @Id 
    private String id;

    @Column(name="email", nullable=false, unique=true)
    private String email;

    @Column(name="password", unique=false, nullable=false)
    private String password;

    @Column(name="username", nullable=false, unique=false)
    private String username;

    @OneToMany(mappedBy="id")
    private List<Task> tasks;


}