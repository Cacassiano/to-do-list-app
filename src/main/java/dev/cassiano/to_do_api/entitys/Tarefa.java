package dev.cassiano.to_do_api.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity(name="Tarefa")
@Table(name = "tarefas")
@Getter
@Setter
@AllArgsConstructor
public class Tarefa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String descricao;

    String criacao;

    Long dono;

    char status;
}
