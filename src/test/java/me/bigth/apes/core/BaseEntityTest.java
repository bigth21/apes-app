package me.bigth.apes.core;

import jakarta.persistence.EntityManager;
import me.bigth.apes.infrastructure.JpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JpaConfig.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BaseEntityTest {
    @Autowired
    TestEntityRepository repository;
    @Autowired
    EntityManager entityManager;

    @Test
    void test() {
        TestEntity entity = repository.save(new TestEntity());
        entityManager.flush();
        entityManager.clear();

        entity = repository.findById(entity.getId())
                .orElseThrow();
        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getCreatedAt()).isNotNull();
        assertThat(entity.getLastModifiedAt()).isNotNull();
        assertThat(entity.getCreatedAt()).isEqualTo(entity.getLastModifiedAt());
    }
}