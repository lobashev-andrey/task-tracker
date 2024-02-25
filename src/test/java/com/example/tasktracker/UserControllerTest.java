package com.example.tasktracker;

import com.example.tasktracker.entity.User;
import com.example.tasktracker.model.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest extends AbstractUserTest {


    @Test
    public void whenGetAll_thenReturnListOfUsers(){
        var expectedData = List.of(
                new User(FIRST_USER_ID, "User 1", "1mail@mail.com"),
                new User(SECOND_USER_ID, "User 2", "2mail@mail.com"));
        webTestClient.get().uri("/tasktracker/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(2)
                .contains(expectedData.toArray(User[]::new));
    }

    @Test
    public void whenGetById_thenReturnById(){
        var expectedBody = new User(FIRST_USER_ID, "User 1", "1mail@mail.com");

        webTestClient.get().uri("/tasktracker/users/{id}", FIRST_USER_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(expectedBody);
    }

    @Test
    public void whenCreateUser_thenReturnCreatedUser(){

        StepVerifier.create(userRepository.count())
                .expectNext(2L)
                        .expectComplete()
                                .verify();

        User newUser = new User(UUID.randomUUID().toString(), "User 3", "3mail@mail.ru");

        webTestClient.post().uri("/tasktracker/users")
                .body(Mono.just(newUser), User.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(element -> {
                    assertNotNull(element.getId());
                    assertEquals(element.getUsername(), "User 3");
                    assertEquals(element.getEmail(), "3mail@mail.ru");
                });
        StepVerifier.create(userRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();


    }

    @Test
    public void whenUpdateUser_thenReturnUpdatedUser(){
        UserModel userModel = new UserModel();
        userModel.setUsername("New username");

        User receivedData = webTestClient.put().uri("/tasktracker/users/{id}", FIRST_USER_ID)
                .body(Mono.just(userModel), UserModel.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();

        assertEquals(receivedData.getUsername(), "New username");
    }

    @Test
    public void whenDeleteUserById_thenRemoveUserFromDB() {

        webTestClient.delete().uri("/tasktracker/users/{id}", FIRST_USER_ID)
                .exchange()
                .expectStatus().isNoContent();

        StepVerifier.create(userRepository.count())
                .expectNext(1L)
                .expectComplete()
                .verify();

    }
}
