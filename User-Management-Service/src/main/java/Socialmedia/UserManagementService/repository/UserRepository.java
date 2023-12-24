package Socialmedia.UserManagementService.repository;

import Socialmedia.UserManagementService.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>,UserCustomRepository {
}
