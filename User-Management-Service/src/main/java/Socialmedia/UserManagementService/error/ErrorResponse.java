package Socialmedia.UserManagementService.error;

import lombok.Data;

@Data
public class ErrorResponse {

  private String error;
  private String message;
}
