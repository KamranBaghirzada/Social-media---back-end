package Socialmedia.UserAuthenticationService.service.auth;

import Socialmedia.UserAuthenticationService.config.JwtService;
import Socialmedia.UserAuthenticationService.dto.*;
import Socialmedia.UserAuthenticationService.exception.EmailExistsException;
import Socialmedia.UserAuthenticationService.exception.UserNotFoundException;
import Socialmedia.UserAuthenticationService.external.ExternalFriendService;
import Socialmedia.UserAuthenticationService.model.entity.User;
import Socialmedia.UserAuthenticationService.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Socialmedia.UserAuthenticationService.mapper.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationAndAuthenticationServiceImpl implements RegistrationAndAuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ExternalFriendService externalFriendService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthenticationAndRegistrationResponse registerUser(UserRegRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailExistsException("Email " + request.getEmail() + " has already been registered");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = userMapper.userRegRequestToUser(request);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        externalFriendService.sendRegistrationInformationToFriendService(user.getEmail());
        return AuthenticationAndRegistrationResponse.builder()
                .token(jwt)
                .build();
    }

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

    public UserAuthorizationResponse authorizeUser(UserAuthorizationRequest request) {
        String token = request.getJwtToken();
        String email = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with such email does not exist"));
        Claims claims = jwtService.extractAllClaims(token);
        List<String> roles = (List<String>) claims.get("roles");
        Boolean hasAuthority = roles.contains(request.getRole());

        return UserAuthorizationResponse.builder()
                .hasAccess(hasAuthority)
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }
}
