package me.bigth.apes.interfaces.web;

import me.bigth.apes.interfaces.web.form.SignInForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SignInController {
    @GetMapping("/sign-in")
    public String signInForm(@ModelAttribute("user") SignInForm form) {
        return "sign-in";
    }
}
