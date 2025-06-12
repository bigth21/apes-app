package me.bigth.apes.application;

import lombok.RequiredArgsConstructor;
import me.bigth.apes.application.exception.AuthorityNotExistException;
import me.bigth.apes.core.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(String username, String password) {
        Authority authority = authorityRepository.findByRole(Role.USER)
                .orElseThrow(AuthorityNotExistException::new);
        userRepository.save(new User(username, passwordEncoder.encode(password), UserState.INACTIVE, List.of(authority)));
    }
}
