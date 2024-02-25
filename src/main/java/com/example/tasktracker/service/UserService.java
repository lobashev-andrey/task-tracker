package com.example.tasktracker.service;

import com.example.tasktracker.entity.User;
import com.example.tasktracker.repository.UserRepository;
import com.example.tasktracker.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Mono<User> save(User user) {
        user.setId(UUID.randomUUID().toString());
        return userRepository.findByUsername(
                user.getUsername())
                .flatMap(existedUser -> {                         // проверка User с таким username:
                            if(existedUser != null) {             // найден - возвращаем new Mono как флаг для контроллера
                                return Mono.just(new User());
                            }
                            return Mono.empty();                  // нет - пустой Mono как флаг для switchIfEmpty
                        })
                .switchIfEmpty(userRepository.save(user));        // и тогда сохраняем юзера
    }

    public Mono<User> update(User user) {
        return findById(user.getId()).flatMap(existedUser -> {
            user.getRoles().addAll(existedUser.getRoles());

            BeanUtils.nonNullPropertiesCopy(user, existedUser);

            return userRepository.save(existedUser);
        });
    }

    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }
}
