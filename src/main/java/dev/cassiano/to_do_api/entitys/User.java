package dev.cassiano.to_do_api.entitys;

import dev.cassiano.to_do_api.DTOs.UserReqDTO;
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

public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id = 0l;
    
    String nome = "null";

    int tarefas = 0;

    String email = "null";

    public User(UserReqDTO req)
    {
        this.email = req.email();
        this.nome = req.nome();
        this.tarefas = req.tarefas();
    }
}
