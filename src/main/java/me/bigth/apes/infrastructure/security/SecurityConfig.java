package me.bigth.apes.infrastructure.security;

import jakarta.servlet.DispatcherType;
import me.bigth.apes.core.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
public class SecurityConfig {
    private static final String[] STATIC_RESOURCE_PATHS = {"/css/**", "/images/**", "/favicon.ico", "/.well-known/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/sign-out")
                        .logoutSuccessUrl("/sign-in?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/sign-in")
                        .loginProcessingUrl("/sign-in"))
                .sessionManagement(session -> session
                        .sessionConcurrency(concurrency -> concurrency
                                .maximumSessions(1)))
                .authorizeHttpRequests(auth -> {
                    configureTestEndpointsAccess(auth);
                    auth
                            .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                            .requestMatchers(HttpMethod.GET, STATIC_RESOURCE_PATHS).permitAll()
                            .requestMatchers(HttpMethod.GET, "/").permitAll()
                            .requestMatchers(HttpMethod.GET, "/sign-in").permitAll()
                            .requestMatchers(HttpMethod.GET, "/sign-up").permitAll()
                            .requestMatchers(HttpMethod.POST, "/sign-up").permitAll()
                            .anyRequest().authenticated();
                });
        return http.build();
    }

    private void configureTestEndpointsAccess(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        if (isProfileEnabled("local") || isProfileEnabled("test")) {
            auth.requestMatchers("/tests/**").permitAll();
        } else {
            auth.requestMatchers("/tests/**").denyAll();
        }
    }

    private boolean isProfileEnabled(String profile) {
        String[] activeProfiles = System.getProperty("spring.profiles.active", "").split(",");
        for (String activeProfile : activeProfiles) {
            if (profile.equals(activeProfile.trim())) {
                return true;
            }
        }
        return false;
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
