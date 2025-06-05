package me.bigth.apes.interfaces.web.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpForm {
    private String username;
    private String password;
    private String confirmPassword;
}
