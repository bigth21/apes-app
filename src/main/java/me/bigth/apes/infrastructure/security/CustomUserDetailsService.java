package me.bigth.apes.infrastructure.security;

import lombok.RequiredArgsConstructor;
import me.bigth.apes.core.User;
import me.bigth.apes.core.UserRepository;
import me.bigth.apes.core.UserState;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameWithAuthorities(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (UserState.INACTIVE.equals(user.getState())) {
            throw new UserInactiveException("User is inactive");
        }

        return CustomUserDetails.from(user);
    }
}
