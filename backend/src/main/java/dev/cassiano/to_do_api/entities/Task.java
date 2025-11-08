package dev.cassiano.to_do_api.entities;

import java.time.LocalDateTime;

import dev.cassiano.to_do_api.dtos.task.TaskReqDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    @Column(name="id", nullable=false, unique=true) 
    private Long id;

    @Column(name="title", nullable = false, unique=false)
    private String title;

    @Column(name="description", nullable = true, unique=false)
    private String description;

    @Column(name = "status", nullable = false, unique = false)
    public String status;

    @Column(name="created_at", nullable= false, unique=false)
    private LocalDateTime created_at;

    @Column(name="updated_at", nullable=false, unique=false)
    private LocalDateTime updated_at;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner_id")
    private User user;

    public Task(TaskReqDTO req, User user) {
        this.created_at = LocalDateTime.now();
        this.updated_at = this.created_at;
        this.title = req.getTitle();
        this.status = req.getStatus();
        this.description = req.getDescription();
        this.user = user;
    }

    public void update(TaskReqDTO req) {
        this.updated_at = LocalDateTime.now();
        this.title = req.getTitle();
        this.status = req.getStatus();
        this.description = req.getDescription();
    }

}
