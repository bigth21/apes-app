package me.bigth.apes.infrastructure;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.bigth.apes.core.Role;
import me.bigth.apes.core.User;
import me.bigth.apes.core.UserState;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static me.bigth.apes.core.UserState.ACTIVE;
import static me.bigth.apes.core.UserState.SUSPENDED;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private UserState state;
    private List<Role> roles;

    public static CustomUserDetails from(User user) {
        var userDetails = new CustomUserDetails();
        userDetails.id = user.getId();
        userDetails.username = user.getUsername();
        userDetails.password = user.getPassword();
        userDetails.state = user.getState();
        userDetails.roles = user.getRoles();
        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return state != SUSPENDED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return state == ACTIVE;
    }
}
