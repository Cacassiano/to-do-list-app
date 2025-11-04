package dev.cassiano.to_do_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.cassiano.to_do_api.entities.Task;
import dev.cassiano.to_do_api.entities.User;
import dev.cassiano.to_do_api.exceptions.customs.NotFoundException;
import dev.cassiano.to_do_api.repositories.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getById(Long id, User user) throws NotFoundException{
        return taskRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new NotFoundException("This task doesn't exists"));
    }

    public void deleteById(Long id, User user) throws NotFoundException{
        taskRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new NotFoundException("This task doesn't exists"));
        taskRepository.deleteById(id);
    }
}
