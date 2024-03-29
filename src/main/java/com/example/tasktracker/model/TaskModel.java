package com.example.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskModel implements ModelResponse{

    private String id;

    private String name;

    private String description;

    private String authorId;

    private String assigneeId;

    private String createdAt;

    private String updatedAt;

    private String status;

    private String[] observerIds = {};
}


