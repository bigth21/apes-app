package me.bigth.apes.interfaces.web;

import me.bigth.apes.core.*;
import me.bigth.apes.infrastructure.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SignInController.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class))
class SignInControllerTest {
    @MockitoBean
    UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void signInForm() throws Exception {
        mockMvc.perform(get("/sign-in"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "text/html;charset=UTF-8"));
    }

    @Test
    @WithAnonymousUser
    void signIn() throws Exception {
        Mockito.when(userRepository.findByUsernameWithAuthorities("user"))
                .thenReturn(Optional.of(new User("user", "{noop}1234", UserState.ACTIVE, List.of(new Authority(Role.USER)))));

        mockMvc.perform(post("/sign-in")
//                        .with(csrf())
                        .param("username", "user")
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithAnonymousUser
    void signIn_inactiveUser() throws Exception {
        Mockito.when(userRepository.findByUsernameWithAuthorities("user"))
                .thenReturn(Optional.of(new User("user", "{noop}1234", UserState.INACTIVE, List.of(new Authority(Role.USER)))));

        mockMvc.perform(post("/sign-in")
                        .with(csrf())
                        .param("username", "user")
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sign-in?error"));
    }

    @Test
    @WithAnonymousUser
    void signIn_badCredential() throws Exception {
        mockMvc.perform(post("/sign-in")
                        .with(csrf())
                        .param("username", "user")
                        .param("password", "12345"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sign-in?error"));
    }
}