package Socialmedia.UserAuthenticationService.dto;

import Socialmedia.UserAuthenticationService.model.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class UserResponse {

    private String firstname;

    private String lastname;

    private String email;

    private String city;

    private String country;

    private String legalAddress;

    private String workplace;

    private String occupation;

    private String bio;

    private LocalDate dateOfBirth;

    private Role role;
}
