package com.example.tasktracker.controller;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.mapper.TaskMapper;
import com.example.tasktracker.mapper.UserMapper;
import com.example.tasktracker.model.ModelResponse;
import com.example.tasktracker.model.OneLineResponse;
import com.example.tasktracker.model.TaskModel;
import com.example.tasktracker.service.TaskService;
import com.example.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasktracker/tasks")
public class TaskController {

    private final TaskService service;

    private final UserService userService;

    private final UserMapper userMapper;

    private final TaskMapper mapper;

    @GetMapping
    public Flux<Tuple3<ModelResponse, ModelResponse, ModelResponse>> getAllTasks() {
        return service.findAll().map(Task::getId).flatMap(this::getTaskById).map(ResponseEntity::getBody);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Tuple3<ModelResponse, ModelResponse, ModelResponse>>> getTaskById(@PathVariable String id) {
        Mono<ModelResponse> task = service.findById(id).map(mapper::taskToTaskResponse)
                .map(taskResponse -> {
                    if(taskResponse.getName().equals("NOT FOUND")){
                        return new OneLineResponse("Task with such id does not exist");
                    }
                    return taskResponse;
                });


        Mono<ModelResponse> author = service.findById(id).flatMap(foundTask -> {
            String authorId = foundTask.getAuthorId();
            return foundTask.getAuthorId() == null
                    ? Mono.just(new OneLineResponse("Author not defined"))
                    : userService.findById(authorId).map(userMapper::userToUserModel);
        });

        Mono<ModelResponse> assignee = service.findById(id).flatMap(foundTask -> {
            String assigneeId = foundTask.getAssigneeId();
            return assigneeId == null
                    ? Mono.just(new OneLineResponse("Assignee not defined"))
                    : userService.findById(assigneeId).map(userMapper::userToUserModel);
        });

        return Mono.zip(task, author, assignee).map(ResponseEntity::ok);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public Mono<ResponseEntity<TaskModel>> createTask(@RequestBody TaskModel model) {
        return service.save(mapper.taskModelToTask(model))
                .map(mapper::taskToTaskModel)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public Mono<ResponseEntity<TaskModel>> updateTask(@PathVariable String id, @RequestBody TaskModel model) {
        return service.update(mapper.taskModelToTask(id, model))
                .map(mapper::taskToTaskModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/{observerId}")
    public Mono<ResponseEntity<TaskModel>> addObserver(@PathVariable String id, @PathVariable String observerId) {
        return service.addObserver(id, observerId)
                .map(mapper::taskToTaskModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public Mono<ResponseEntity<Void>> deleteTaskById(@PathVariable String id) {
        return service.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }
}
