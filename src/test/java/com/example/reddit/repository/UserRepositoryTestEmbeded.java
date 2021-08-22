package com.example.reddit.repository;

import com.example.reddit.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTestEmbeded {

    @Autowired
    private UserRepository userRepository;


    @Test
    @Sql("classpath:test-data.sql")
    public void shouldSaveUsersThroughSqlFile() {
        Optional<User> test = userRepository.findByUsername("testuser_sql");
        assertThat(test).isNotEmpty();
    }

    @Test
    @DisplayName(" ")
    public void shouldSaveUser(){
        User user = new User(null, "test user", "secret password", "user@email.com", Instant.now(), true);

        User savedUser  = userRepository.save(user);
        assertThat(savedUser).usingRecursiveComparison().ignoringFields("userId").isEqualTo(user);
    }


}
