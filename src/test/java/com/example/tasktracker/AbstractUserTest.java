package com.example.tasktracker;

import com.example.tasktracker.entity.User;
import com.example.tasktracker.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
public class AbstractUserTest {

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected UserRepository userRepository;

    protected static String FIRST_USER_ID = UUID.randomUUID().toString();
    protected static String SECOND_USER_ID = UUID.randomUUID().toString();

    @Container
    static MongoDBContainer container = new MongoDBContainer("mongo:6:0:8")
            .withReuse(true);

    @DynamicPropertySource
    static void setProperties (DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
    }

    @BeforeEach
    public void setup() {
//        userRepository.save(new User(FIRST_USER_ID, "User 1", "1mail@mail.com")).block();
//        userRepository.save(new User(SECOND_USER_ID, "User 2", "2mail@mail.com")).block();
        userRepository.saveAll(List.of(
                new User(FIRST_USER_ID, "User 1", "1mail@mail.com"),
                new User(SECOND_USER_ID, "User 2", "2mail@mail.com")
        )).collectList().block();
    }

    @AfterEach
    public void clean() {
        userRepository.deleteAll().block();
    }
}
