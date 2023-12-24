package Socialmedia.UserAuthenticationService.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "Password must contain 8-20 characters, including at least one upper case letter and one symbol";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // Regular expression for password validation
    String regex() default "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
}
