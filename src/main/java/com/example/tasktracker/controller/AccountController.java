package com.example.tasktracker.controller;

import com.example.tasktracker.mapper.UserMapper;
import com.example.tasktracker.model.ModelResponse;
import com.example.tasktracker.model.OneLineResponse;
import com.example.tasktracker.model.UserModel;
import com.example.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasktracker/account")
public class AccountController {

    private final UserService service;

    private final UserMapper mapper;

    @PostMapping
    public Mono<ResponseEntity<ModelResponse>> createUser(@RequestBody UserModel model) {
        return service.save(
                mapper.userModelToUser(model))
                .map(user -> {
                    if(user.getId() == null) {     // Если сервис вернул флаг в виде new User()
                        return new OneLineResponse("Юзер с таким именем уже существует, придумайте другое");
                    }
                    return mapper.userToUserModel(user);
                })
                .map(ResponseEntity::ok);
    }
}
