package Socialmedia.UserManagementService.map;

import Socialmedia.UserManagementService.dto.UserResponse;
import Socialmedia.UserManagementService.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "firstname", target = "firstname")
//    @Mapping(source = "lastname", target = "lastname")
//    @Mapping(source = "patronymic", target = "patronymic")
    @Mapping(source = "email", target = "email")
    UserResponse userToUserResponse(User user);

//    @Mapping(source = "firstname", target = "firstname")
//    @Mapping(source = "lastname", target = "lastname")
//    @Mapping(source = "patronymic", target = "patronymic")
//    @Mapping(target = "id", ignore = true)
//    void userRequestToUser(UserRequest userRequest, User user);
}
