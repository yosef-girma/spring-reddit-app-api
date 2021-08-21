package com.example.reddit.service;

import com.example.reddit.dto.PostResponse;
import com.example.reddit.mapper.PostMapper;
import com.example.reddit.models.Post;
import com.example.reddit.repository.PostRepository;
import com.example.reddit.repository.SubredditRepository;
import com.example.reddit.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {

    private final PostRepository postRepository = Mockito.mock(PostRepository.class);
    private final SubredditRepository subredditRepository = Mockito.mock(SubredditRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final AuthService authService = Mockito.mock(AuthService.class);
    private final PostMapper postMapper = Mockito.mock(PostMapper.class);

    @Test
    @DisplayName("Should find post by id")
    void shouldFindPostById() {
        PostService postService = new PostService(postRepository,subredditRepository,userRepository,authService,postMapper);

        Post post = new Post(123L, "First Post", "http://url.site", "Test",
                0, null, Instant.now(), null);
        PostResponse expectedPostResponse = new PostResponse(123L, "First Post", "http://url.site", "Test",
                "Test User", "Test Subredit", 0, 0, "1 Hour Ago", false, false);

        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(expectedPostResponse);

        PostResponse actualPostResponse = postService.getPostById(123L);
        assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
        assertThat(actualPostResponse.getPostName()).isEqualTo(expectedPostResponse.getPostName());

    }
}