package com.example.tasktracker.controller;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.mapper.TaskMapper;
import com.example.tasktracker.model.TaskModel;
import com.example.tasktracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasktracker/tasks")
public class TaskController {

    private final TaskService service;

    private final TaskMapper mapper;

    @GetMapping
    public Flux<TaskModel> getAllTasks() {
        return service.findAll().map(mapper::taskToTaskModel);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TaskModel>> getTaskById(@PathVariable String id) {
        return service.findById(id)
                .map(mapper::taskToTaskModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<TaskModel>> createTask(@RequestBody TaskModel model) {
        return service.save(mapper.taskModelToTask(model))
                .map(mapper::taskToTaskModel)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<TaskModel>> updateTask(@PathVariable String id, @RequestBody TaskModel model) {
        return service.update(mapper.taskModelToTask(id, model))
                .map(mapper::taskToTaskModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTaskById(@PathVariable String id) {
        return service.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }





    @DeleteMapping
    public void deleteAll() {
        service.clean();
    }


}
