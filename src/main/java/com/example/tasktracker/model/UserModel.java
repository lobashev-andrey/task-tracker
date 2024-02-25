package com.example.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements ModelResponse{

    private String id;

    private String username;

    private String password;

    private String email;

    private String role;
}

