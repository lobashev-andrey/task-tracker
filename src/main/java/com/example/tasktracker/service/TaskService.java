package com.example.tasktracker.service;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.repository.TaskRepository;
import com.example.tasktracker.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public Flux<Task> findAll() {
        return repository.findAll();
    }

    public Mono<Task> findById(String id) {
        return repository.findById(id);
    }

    public Mono<Task> save(Task task) {
        task.setId(UUID.randomUUID().toString());
//        task.setCreatedAt(Instant.now());
//        task.setUpdatedAt(Instant.now());
        return repository.save(task);
    }

    public Mono<Task> update(Task task) {
        return findById(task.getId()).flatMap(taskToUpdate -> {
            BeanUtils.nonNullPropertiesCopy(task, taskToUpdate);
//            taskToUpdate.setCreatedAt(Instant.now());
            return repository.save(taskToUpdate);
        });
    }

    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }




    public void clean(){
        repository.deleteAll();
    }
}

