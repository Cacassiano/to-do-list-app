package dev.cassiano.to_do_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.cassiano.to_do_api.entities.Task;
import dev.cassiano.to_do_api.exceptions.customs.NotFoundException;
import dev.cassiano.to_do_api.repositories.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getById(Long id, String user_email) throws NotFoundException{
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("This task doesn't exists"));
        if(task.getUser().getEmail() != user_email) throw new NotFoundException("This task doesn't exists");
        return task;
    }

    public void deleteById(Long id, String user_email) throws NotFoundException{
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("This task doesn't exists"));
        if(task.getUser().getEmail() != user_email) throw new NotFoundException("This task doesn't exists");
        taskRepository.deleteById(id);
    }
}
