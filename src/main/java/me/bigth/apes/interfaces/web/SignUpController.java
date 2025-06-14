package me.bigth.apes.interfaces.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.bigth.apes.application.UserService;
import me.bigth.apes.core.User;
import me.bigth.apes.core.UserRepository;
import me.bigth.apes.interfaces.web.form.SignUpForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class SignUpController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/sign-up")
    public String signUpForm(@ModelAttribute("user") SignUpForm form) {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute("user") @Valid SignUpForm form,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "sign-up";
        }

        Optional<User> maybeUser = userRepository.findByUsername(form.getUsername());
        if (maybeUser.isPresent()) {
            result.rejectValue("username","sign-up.username.duplicate");
            return "sign-up";
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            result.rejectValue("password", "sign-up.password.mismatch");
            return "sign-up";
        }
        userService.signUp(form.getUsername(), form.getPassword());
        // TODO: Send email verification link or code
        return "redirect:/sign-in";
    }
}
