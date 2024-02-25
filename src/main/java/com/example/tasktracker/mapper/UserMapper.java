package com.example.tasktracker.mapper;

import com.example.tasktracker.entity.RoleType;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User userModelToUser(UserModel userModel) {
        Set<RoleType> roles = new HashSet<>();
        if (userModel.getRole().equals("USER")) roles.add(RoleType.ROLE_USER);
        if (userModel.getRole().equals("MANAGER")) roles.add(RoleType.ROLE_MANAGER);

        return new User(userModel.getId(),
                userModel.getUsername(),
                passwordEncoder.encode(userModel.getPassword()),
                userModel.getEmail(),
                roles);
    }

    public User userModelToUser(String userId, UserModel userModel) {
        User user = userModelToUser(userModel);
        user.setId(userId);
        return user;
    }

    public UserModel userToUserModel(User user){
        String role = StringUtils.collectionToCommaDelimitedString(user.getRoles());

        return new UserModel(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), role);
    }
}
