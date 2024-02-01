package com.example.tasktracker.mapper;

import com.example.tasktracker.entity.User;
import com.example.tasktracker.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User userModelToUser(UserModel userModel);

    default User userModelToUser(String userId, UserModel userModel){
        User user = userModelToUser(userModel);
        user.setId(userId);
        return user;
    };

    UserModel userToUserModel(User user);
}
