package com.example.tasktracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    private String name;

    private String description;

    private String authorId;

    private String assigneeId;



}
//    private Instant createdAt;
//
//    private Instant updatedAt;

//    private TaskStatus status;



//    private Set<String> observerIds;

//    @ReadOnlyProperty
//    private User author;
//
//    @ReadOnlyProperty
//    private User assignee;
//
//    @ReadOnlyProperty
//    private Set<User> observers;