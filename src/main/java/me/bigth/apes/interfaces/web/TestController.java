package me.bigth.apes.interfaces.web;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Profile({"local", "test"})
@Controller
public class TestController {
    @GetMapping("/tests/500")
    public String test500() {
        throw new RuntimeException();
    }
}
