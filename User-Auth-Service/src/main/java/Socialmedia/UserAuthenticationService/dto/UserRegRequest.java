package Socialmedia.UserAuthenticationService.dto;

import Socialmedia.UserAuthenticationService.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import Socialmedia.UserAuthenticationService.validator.ValidPassword;
import Socialmedia.UserAuthenticationService.validator.ValidRole;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegRequest {

    @NotBlank
    @Size(min = 2, max = 15,message = "Name of user must contain at least 2, at most 15 letters")
    private String firstname;

    @NotBlank
    @Size(min = 2, max = 15,message = "Lastname of user must contain at least 2, at most 15 letters")
    private String lastname;

    @Email(message = "Included email is not in correct form")
    private String email;

    @ValidPassword
    private String password;

    private String city;

    @NotBlank
    @Size(min = 2, max = 25,message = "Lastname of user must contain at least 2, at most 15 letters")
    private String country;

    private String legalAddress;

    private String workplace;

    private String occupation;

    private String bio;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;

    @ValidRole(message = "Role of user should be either USER or ADMIN. ")
    private Role role;
}
