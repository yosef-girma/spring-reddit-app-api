package com.example.reddit.repository;

import com.example.reddit.BaseTest;
import com.example.reddit.models.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest extends BaseTest {


    @Autowired
    private PostRepository postRepository;

    @Test
    public void shouldSavePost(){
        Post post = new Post(null, "First Post", "http://url.site", "Test",
                0, null, Instant.now(), null);
        Post actualPost = postRepository.save(post);

        assertThat(actualPost).usingRecursiveComparison().ignoringFields("postId").isEqualTo(post);

    }


}
