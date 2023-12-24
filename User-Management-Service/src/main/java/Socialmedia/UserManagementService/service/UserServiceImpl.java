package Socialmedia.UserManagementService.service;

import Socialmedia.UserManagementService.dto.UserRequest;
import Socialmedia.UserManagementService.dto.UserResponse;
import Socialmedia.UserManagementService.model.entity.User;
import Socialmedia.UserManagementService.map.UserMapper;
import Socialmedia.UserManagementService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<UserResponse> getUser(UserRequest criteria) {
        List<User> usersWithGivenCriteria = userRepository.findUsersByCriteria(criteria);

        return usersWithGivenCriteria.stream()
                .map(userMapper::userToUserResponse).toList();
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest request) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
