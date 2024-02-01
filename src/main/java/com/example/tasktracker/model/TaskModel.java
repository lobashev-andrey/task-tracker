package com.example.tasktracker.model;

import com.example.tasktracker.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskModel {

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