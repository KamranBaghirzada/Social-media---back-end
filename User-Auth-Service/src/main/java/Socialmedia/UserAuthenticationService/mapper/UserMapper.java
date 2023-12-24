package Socialmedia.UserAuthenticationService.mapper;

import Socialmedia.UserAuthenticationService.dto.UserRegRequest;
import Socialmedia.UserAuthenticationService.dto.UserResponse;
import Socialmedia.UserAuthenticationService.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userRegRequestToUser(UserRegRequest userRegRequest);

    UserResponse UserToUserResponse(User user);
}
