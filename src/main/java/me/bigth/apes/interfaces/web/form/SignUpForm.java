package me.bigth.apes.interfaces.web.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpForm {
    @Email(message = "Email format is invalid.")
    private String username;
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$",
            message = "Password must be at least 8 characters long and include letters, numbers, and special characters.")
    private String password;
    private String confirmPassword;
}
