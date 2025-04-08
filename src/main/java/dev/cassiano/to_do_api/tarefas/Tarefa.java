package dev.cassiano.to_do_api.tarefas;

import java.time.OffsetDateTime;

import dev.cassiano.to_do_api.tarefas.dtos.TarefasDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="Tarefa")
@Table(name = "tarefas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String title;

    String descricao;

    String criacao;

    String dono;

    boolean concluida;
    public boolean getConcluida() {
        return this.concluida;
    }
    
    public Tarefa(TarefasDTO req, String dono) {
        this.dono = dono;
        this.concluida = req.concluida();
        this.descricao = req.descricao();
        this.title = req.title();
        this.criacao = ""+OffsetDateTime.now();
    }
}
