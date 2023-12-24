package Socialmedia.UserManagementService.service;

import Socialmedia.UserManagementService.dto.UserRequest;
import Socialmedia.UserManagementService.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getUser(UserRequest criteria);

    UserResponse updateUser(Long userId, UserRequest request);

    void deleteUser(Long userId);
}
