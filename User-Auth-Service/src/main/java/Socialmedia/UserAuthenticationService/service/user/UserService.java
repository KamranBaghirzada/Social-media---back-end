package Socialmedia.UserAuthenticationService.service.user;

import Socialmedia.UserAuthenticationService.dto.*;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface UserService {

    AuthenticationAndRegistrationResponse registerUser(UserRegRequest request);

    AuthenticationAndRegistrationResponse authenticateUser(UserAuthenticationRequest request);

//    UserResponse getUser(Long userId);

    UserResponse updateUser(Integer userId, UserUpdateRequest request);

    DetailsResponse getDetails(String authHeader);

    String addProfilePhoto(MultipartFile multipartFile) throws IOException;

    public Resource getProfilePhoto(String imageUrl) throws Exception;

    void deleteProfilePhoto(Integer userId) throws IOException;
}
