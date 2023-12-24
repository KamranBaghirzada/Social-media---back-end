package Socialmedia.UserAuthenticationService.validator;

import Socialmedia.UserAuthenticationService.model.enums.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class RoleValidator implements ConstraintValidator<ValidRole, Role> {

    @Override
    public void initialize(ValidRole constraintAnnotation) {
    }

    @Override
    public boolean isValid(Role role, ConstraintValidatorContext context) {
        // Check if the role is one of the enum values
        return Arrays.asList(Role.values()).contains(role);
    }
}
