package Socialmedia.UserManagementService.dto;

import lombok.Data;

@Data
public class AuthorizationResponse {

    private Boolean hasAccess;

    private Integer userId;
}
