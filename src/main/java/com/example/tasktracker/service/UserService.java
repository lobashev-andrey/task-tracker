package com.example.tasktracker.service;

import com.example.tasktracker.entity.User;
import com.example.tasktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Flux<User> findAll(){
        return userRepository.findAll();
    }

    public Mono<User> findById(String id){
        return userRepository.findById(id);
    }

    public Mono<User> save(User user) {
        user.setId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    public Mono<User> update(User user) {
        return findById(user.getId()).flatMap(existedUser -> {
            if(StringUtils.hasText(user.getEmail())){
                existedUser.setEmail(user.getEmail());
            }
            if(StringUtils.hasText(user.getUsername())){
                existedUser.setUsername(user.getUsername());
            }
            return userRepository.save(existedUser);
        });
    }

    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }
}
