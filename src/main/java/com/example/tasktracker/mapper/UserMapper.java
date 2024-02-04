package com.example.tasktracker.mapper;

import com.example.tasktracker.entity.User;
import com.example.tasktracker.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User userModelToUser(UserModel userModel) {
        return new User(userModel.getId(), userModel.getUsername(), userModel.getEmail());
    }

    public User userModelToUser(String userId, UserModel userModel) {
        User user = this.userModelToUser(userModel);
        user.setId(userId);
        return user;
    }

    public UserModel userToUserModel(User user){
        return new UserModel(user.getId(), user.getUsername(), user.getEmail());
    }
}


//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
//public interface UserMapper {
//
//    User userModelToUser(UserModel userModel);
//
//    default User userModelToUser(String userId, UserModel userModel){
//        User user = userModelToUser(userModel);
//        user.setId(userId);
//        return user;
//    };
//
//    UserModel userToUserModel(User user);
//}
