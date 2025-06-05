package me.bigth.apes.interfaces.web;

import me.bigth.apes.interfaces.web.form.SignUpForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SignUpController {
    @GetMapping("/sign-up")
    public String signUp(@ModelAttribute("user") SignUpForm form) {
        return "sign-up";
    }
}
