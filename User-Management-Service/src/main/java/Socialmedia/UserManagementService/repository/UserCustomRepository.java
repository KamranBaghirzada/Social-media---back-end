package Socialmedia.UserManagementService.repository;

import Socialmedia.UserManagementService.dto.UserRequest;
import Socialmedia.UserManagementService.model.entity.User;

import java.util.List;

public interface UserCustomRepository {

     List<User> findUsersByCriteria(UserRequest criteria);

}
