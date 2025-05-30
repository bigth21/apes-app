package me.bigth.apes.interfaces.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {
    @GetMapping("/sign-in")
    public String signInForm(Model model) {
        model.addAttribute("user", new SignInForm());
        return "sign-in";
    }
}
