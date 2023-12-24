package Socialmedia.UserAuthenticationService.dto;

import Socialmedia.UserAuthenticationService.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {

    private String firstname;

    private String lastname;

    private String city;

    private String country;

    private String legalAddress;

    private String workplace;

    private String occupation;

    private String bio;

    private LocalDate dateOfBirth;

    private Role role;
}
