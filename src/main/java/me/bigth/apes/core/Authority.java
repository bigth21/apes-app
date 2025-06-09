package me.bigth.apes.core;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = @Index(name = "idx_role", columnList = "role", unique = true))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Authority extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private Role role;

    public Authority(Role role) {
        this.role = role;
    }
}
