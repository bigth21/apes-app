package me.bigth.apes.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select distinct u from User u " +
            "join fetch u.userAuthorities ua " +
            "join fetch ua.authority " +
            "where u.username = :username ")
    Optional<User> findByUsernameWithAuthorities(String username);
}
