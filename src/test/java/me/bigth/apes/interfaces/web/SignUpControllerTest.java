package me.bigth.apes.interfaces.web;

import me.bigth.apes.application.UserService;
import me.bigth.apes.core.UserRepository;
import me.bigth.apes.infrastructure.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SignUpController.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class))
class SignUpControllerTest {
    @MockitoBean
    UserRepository userRepository;
    @MockitoBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void signUpForm() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "text/html;charset=UTF-8"));
    }

    @Test
    @WithAnonymousUser
    void signUp() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .with(csrf())
                        .param("username", "username@domain.com")
                        .param("password", "abcabc1234!")
                        .param("confirmPassword", "abcabc1234!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sign-in"));
    }
}