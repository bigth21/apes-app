package me.bigth.apes.interfaces.web.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpForm {
    @Email(message = "{sign-up.username.format.invalid}")
    private String username;
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$",
            message = "{sign-up.password.pattern.invalid}")
    private String password;
    private String confirmPassword;
}
