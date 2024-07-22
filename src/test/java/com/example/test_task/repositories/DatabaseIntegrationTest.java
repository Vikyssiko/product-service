package com.example.test_task.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

@DataJpaTest
@Sql(value = "/scripts/RESET_DATA.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class DatabaseIntegrationTest {
    @Autowired
    protected TestEntityManager entityManager;
    @Autowired
    protected DataSource dataSource;

    protected void saveAndFlush(final Object... entities) {
        for (var entity : entities) {
            entityManager.persist(entity);
        }
        entityManager.flush();
        entityManager.clear();
    }
}
