package Socialmedia.UserManagementService.dto;

import lombok.Data;

import javax.management.relation.Role;
import java.time.LocalDate;

@Data
public class UserRequest {

    private String firstname;

    private String lastname;

    private String city;

    private String country;

    private String legalAddress;

    private String workplace;

    private String occupation;

    private String bio;

    private LocalDate dateOfBirth;

    private String role;
}
