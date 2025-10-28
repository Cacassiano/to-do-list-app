package dev.cassiano.to_do_api.dtos;

import java.time.LocalDateTime;

import dev.cassiano.to_do_api.entities.Task;

public record TaskResDTO(
    Long id,
    String title, 
    String description, 
    String status, 
    LocalDateTime created_at,
    LocalDateTime updated_at
) {

    public TaskResDTO(Task task) {
        this(
            task.getId(),
            task.getTitle(),
            task.getDiscription(),
            task.getStatus(),
            task.getCreated_at(),
            task.getUpdated_at()
        );
    }
}
