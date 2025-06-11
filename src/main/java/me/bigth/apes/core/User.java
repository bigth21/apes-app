package me.bigth.apes.core;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(indexes = @Index(name = "idx_username", columnList = "username", unique = true))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {
    @Column(nullable = false)
    private String username;
    @Column(nullable = false, length = 128)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private UserState state;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<UserAuthority> userAuthorities;

    public User(String username, String password, List<Authority> authorities) {
        this.username = username;
        this.state = UserState.INACTIVE;
        this.password = password;
        this.userAuthorities = authorities.stream()
                .map(a -> new UserAuthority(this, a))
                .toList();
    }

    public List<Role> getRoles() {
        return userAuthorities.stream()
                .map(ua -> ua.getAuthority().getRole())
                .toList();
    }
}
