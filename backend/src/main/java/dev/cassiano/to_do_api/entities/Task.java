package dev.cassiano.to_do_api.entities;

import java.time.LocalDateTime;

import dev.cassiano.to_do_api.dtos.TaskReqDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name="tasks")
@Table(name="tasks")
@Getter
@NoArgsConstructor
public class Task {
    @Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE) 
    @Column(name="id", nullable=false, unique=true) 
    private Long id;

    @Column(name="title", nullable = false, unique=false)
    private String title;

    @Column(name="discription", nullable = true, unique=false)
    private String discription;

    @Column(name = "status", nullable = false, unique = false)
    public String status;

    @Column(name="created-at", nullable= false, unique=false)
    private LocalDateTime created_at;

    @Column(name="updated-at", nullable=false, unique=false)
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name="owner")
    private User user;

    public Task(TaskReqDTO req, User user) {
        this.created_at = LocalDateTime.now();
        this.updated_at = this.created_at;
        this.title = req.getTitle();
        this.status = req.getStatus();
        this.discription = req.getDescription();
        this.user = user;
    }

}
