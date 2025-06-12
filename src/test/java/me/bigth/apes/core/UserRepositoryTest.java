package me.bigth.apes.core;

import jakarta.persistence.EntityManager;
import me.bigth.apes.infrastructure.JpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JpaConfig.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    void findByUsernameWithAuthorities() {
        // Given
        List<Authority> authorities = List.of(new Authority(Role.USER), new Authority(Role.ADMIN));
        authorityRepository.saveAll(authorities);

        userRepository.save(new User("username", "password", UserState.ACTIVE, authorities));

        entityManager.flush();
        entityManager.clear();

        // When
        User user = userRepository.findByUsernameWithAuthorities("username")
                .orElseThrow();
        assertThat(user.getUserAuthorities()).hasSize(2);
        assertThat(user.getUserAuthorities().get(0).getAuthority().getRole()).isEqualTo(Role.USER);
        assertThat(user.getUserAuthorities().get(1).getAuthority().getRole()).isEqualTo(Role.ADMIN);
    }
}