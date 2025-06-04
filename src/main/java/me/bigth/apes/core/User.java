package me.bigth.apes.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = @Index(name = "idx_username", columnList = "username", unique = true))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {
    @Column(nullable = false)
    private String username;
    @Column(nullable = false, length = 128)
    private String password;
}
