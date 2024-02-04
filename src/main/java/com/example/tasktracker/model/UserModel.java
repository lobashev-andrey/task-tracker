package com.example.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements ModelResponse{

    private String id;

    private String username;

    private String email;
}

