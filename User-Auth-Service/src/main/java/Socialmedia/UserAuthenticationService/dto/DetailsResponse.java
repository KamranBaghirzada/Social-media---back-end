package Socialmedia.UserAuthenticationService.dto;

import Socialmedia.UserAuthenticationService.model.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.io.InputStream;


@Data
@Builder
public class DetailsResponse {

    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private Role role;

    private String profilePhotoPath;
}
