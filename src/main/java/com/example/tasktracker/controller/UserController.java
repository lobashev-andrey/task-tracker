package com.example.tasktracker.controller;

import com.example.tasktracker.mapper.UserMapper;
import com.example.tasktracker.model.UserModel;
import com.example.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasktracker/users")
public class UserController {

    private final UserService service;

    private final UserMapper mapper;

    @GetMapping
    public Flux<UserModel> getAllUsers() {
        return service.findAll().map(mapper::userToUserModel);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserModel>> getUserById(@PathVariable String id) {
        return service.findById(id)
                .map(mapper::userToUserModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<UserModel>> createUser(@RequestBody UserModel model) {
        return service.save(mapper.userModelToUser(model))
                .map(mapper::userToUserModel)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserModel>> updateUser(@PathVariable String id, @RequestBody UserModel model) {
        return service.update(mapper.userModelToUser(id, model))
                .map(mapper::userToUserModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable String id) {
        return service.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
