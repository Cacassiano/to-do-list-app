package dev.cassiano.to_do_api.dtos;

import dev.cassiano.to_do_api.services.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TaskReqDTO {
    @NotBlank
    private final String title;
    private final String description;
    private final String status;

    public TaskReqDTO(String title, String description, String status) throws IllegalArgumentException{
        this.title = title;
        this.description = description;
        Status.valueOf(status);
        this.status = status.toUpperCase();
    }
}
