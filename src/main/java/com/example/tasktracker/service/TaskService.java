package com.example.tasktracker.service;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.repository.TaskRepository;
import com.example.tasktracker.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public Flux<Task> findAll() {
        return repository.findAll();
    }

    public Mono<Task> findById(String id) {
        Task defaultTask = new Task();
        defaultTask.setName("NOT FOUND");
        return repository.findById(id).defaultIfEmpty(defaultTask);
    }


    public Mono<Task> save(Task task) {
        task.setId(UUID.randomUUID().toString());
        task.setCreatedAt(String.valueOf(Instant.now()));
        task.setUpdatedAt(String.valueOf(Instant.now()));
        return repository.save(task);
    }

    public Mono<Task> update(Task task) {
        return findById(task.getId()).flatMap(taskToUpdate -> {
            BeanUtils.nonNullPropertiesCopy(task, taskToUpdate);
            taskToUpdate.setUpdatedAt(String.valueOf(Instant.now()));

            return repository.save(taskToUpdate);
        });
    }

    public Mono<Task> addObserver(String id, String newObserverId) {
        return findById(id).flatMap(taskToUpdate -> {
            Set<String> observers = Arrays.stream(taskToUpdate.getObserverIds()).collect(Collectors.toSet());
            observers.add(newObserverId);
            taskToUpdate.setObserverIds(observers.toArray(new String[0]));

            return repository.save(taskToUpdate);
        });
    }

    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }





}

