package com.example.reddit.service;

import com.example.reddit.dto.PostRequest;
import com.example.reddit.dto.PostResponse;
import com.example.reddit.exceptions.PostNotFoundException;
import com.example.reddit.exceptions.SpringRedditException;
import com.example.reddit.exceptions.SubredditNotFoundException;
import com.example.reddit.exceptions.UsernameNotFoundException;
import com.example.reddit.mapper.PostMapper;
import com.example.reddit.models.Post;
import com.example.reddit.models.Subreddit;
import com.example.reddit.models.User;
import com.example.reddit.repository.PostRepository;
import com.example.reddit.repository.SubredditRepository;
import com.example.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public Post savePost(PostRequest postRequest) {
//
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() ->
                        new SpringRedditException("Subreddit not found")
                );
        User currentUser = authService.getCurrentUser();
      return  postRepository.save(postMapper.map(postRequest, subreddit, currentUser));
    }

    @Transactional
    public List<PostResponse> getPosts() {
        List<Post> postList = postRepository.findAll();
        return
                postList.stream().map(postMapper::mapToDto).collect(Collectors.toList());

    }

    @Transactional()
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(" Post Not Found with id " + id));
        return postMapper.mapToDto(post);
    }

    @Transactional
    public List<PostResponse> getPostsBySubreddit(Long subredditId){
        Subreddit subreddit = subredditRepository
                .findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException("Sub reddit not foudn"));

        List<Post> fetchedPostsBySubreddit = postRepository.findAllBySubreddit(subreddit);

      List<PostResponse> mappedToResponse =
              fetchedPostsBySubreddit.stream().map(postMapper::mapToDto).collect(Collectors.toList());

      return mappedToResponse;

    }
    @Transactional
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not foudn"));
        List<PostResponse> postResponseList = postRepository.findByUser(user)
                .stream().map(postMapper::mapToDto).collect(Collectors.toList());
        return postResponseList;
    }


}
