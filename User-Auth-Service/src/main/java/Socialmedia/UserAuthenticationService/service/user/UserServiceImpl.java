package Socialmedia.UserAuthenticationService.service.user;

import Socialmedia.UserAuthenticationService.config.JwtService;
import Socialmedia.UserAuthenticationService.dto.*;
import Socialmedia.UserAuthenticationService.exception.UserNotFoundException;
import Socialmedia.UserAuthenticationService.model.entity.User;
import Socialmedia.UserAuthenticationService.model.enums.Role;
import Socialmedia.UserAuthenticationService.repository.UserRepository;
import Socialmedia.UserAuthenticationService.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import Socialmedia.UserAuthenticationService.mapper.UserMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    private final UserUtil userUtil;

    private static final String UPLOAD_FOLDER = "C:\\Users\\kamran\\Desktop\\SM-images";


    @Override
    public AuthenticationAndRegistrationResponse registerUser(UserRegRequest request) {
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return AuthenticationAndRegistrationResponse.builder()
                .token(jwt)
                .build();
    }

    @Override
    public AuthenticationAndRegistrationResponse authenticateUser(UserAuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        String jwt = jwtService.generateToken(user);
        return AuthenticationAndRegistrationResponse.builder()
                .token(jwt)
                .build();
    }

//    @Override
//    public UserResponse getUser(Long userId) {
//        User user = getUserById(userId);
//        return userMapper.userToUserResponse(user);
//    }

    @Override
    public UserResponse updateUser(Integer userId, UserUpdateRequest request) {
        User user = getUserById(userId);
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        userRepository.save(user);
        return userMapper.UserToUserResponse(user);
    }

    private User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found ."));
    }

    public DetailsResponse getDetails(String authHeader) {
        String email = userUtil.getEmailOfUserFromSecurityContextHolder();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User does not exist. "));
        return DetailsResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole())
                .profilePhotoPath(user.getProfilePhotoFilePath())
                .build();
    }

    public String addProfilePhoto(MultipartFile file) throws IOException {
        File uploadFolder = new File(UPLOAD_FOLDER);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        String uniqueFileName = System.currentTimeMillis() + "_" + file.getName();
        Path filePath = Paths.get(UPLOAD_FOLDER, uniqueFileName);
        file.transferTo(filePath.toFile());

        String email = userUtil.getEmailOfUserFromSecurityContextHolder();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User does not exist. "));
        user.setProfilePhotoFilePath(filePath.toString());
        userRepository.save(user);

        return filePath.toString();
    }

    public Resource getProfilePhoto(String imageUrl) throws Exception {
        String decodedUrl = URLDecoder.decode(imageUrl, "UTF-8");
        Path imagePath = Paths.get(decodedUrl);
        URI uri = imagePath.toUri();
        return new UrlResource(uri);
    }

    @Override
    @Transactional
    public void deleteProfilePhoto(Integer userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));
        String imageURL = user.getProfilePhotoFilePath();
        deletePhotoFromFolder(imageURL);
        user.setProfilePhotoFilePath(null);
        userRepository.save(user);
    }

    private void deletePhotoFromFolder(String imageURL) throws IOException {
        Path filePath = Paths.get(imageURL);

        // Delete the file
        Files.delete(filePath);
    }
}
