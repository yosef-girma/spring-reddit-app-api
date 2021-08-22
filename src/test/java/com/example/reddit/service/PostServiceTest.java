package com.example.reddit.service;

import com.example.reddit.dto.PostRequest;
import com.example.reddit.dto.PostResponse;
import com.example.reddit.mapper.PostMapper;
import com.example.reddit.models.Post;
import com.example.reddit.models.Subreddit;
import com.example.reddit.models.User;
import com.example.reddit.repository.PostRepository;
import com.example.reddit.repository.SubredditRepository;
import com.example.reddit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    /*
     BeforeEach
     Mockito

     */
    @Mock
    private  PostRepository postRepository;
    @Mock
    private  SubredditRepository subredditRepository;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private AuthService authService ;
    @Mock
    private PostMapper postMapper;
    @Captor
    private  ArgumentCaptor<Post>  postArgumentCaptor;

    private PostService postService;

    @BeforeEach
    public  void setup(){
        postService = new PostService(postRepository,subredditRepository,userRepository,authService,postMapper);
    }
    @Test
    @DisplayName("Should find post by id")
    void findPostById() {

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

    @Test
    @DisplayName("Should save post ")
    void shouldSavePost(){

        User user = new User(12L,"hello","pass","yoef.gkel@gmail.com",Instant.now(),false);
        PostRequest postRequest = new PostRequest(1L,"test1","post name","http://hell.com","description");
        Subreddit subreddit = new Subreddit(123L, "First Subreddit", "Subreddit Description", emptyList(), Instant.now(), user);

        Post post = new Post(123L, "First Post", "http://url.site", "Test",
                0, null, Instant.now(), null);


        Mockito.when(subredditRepository.findByName(postRequest.getSubredditName())).thenReturn(Optional.of(subreddit));

        Mockito.when(postMapper.map(postRequest,subreddit,user)).thenReturn(post);
        Mockito.when(authService.getCurrentUser()).thenReturn(user);
        postService.savePost(postRequest);

        Mockito.verify(postRepository,Mockito.times(1))
                .save(postArgumentCaptor.capture());

        postArgumentCaptor.getValue();
        assertThat(post.getPostId()).isEqualTo(123L);
        assertThat(post.getPostName()).isEqualTo("First Post");



    }
}