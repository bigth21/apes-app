package me.bigth.apes.interfaces.web.form;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpForm {
    @Email(message = "Email format is invalid.")
    private String username;
    private String password;
    private String confirmPassword;
}
