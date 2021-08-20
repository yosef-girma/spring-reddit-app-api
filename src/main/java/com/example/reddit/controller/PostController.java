package com.example.reddit.controller;

import com.example.reddit.dto.PostRequest;
import com.example.reddit.dto.PostResponse;
import com.example.reddit.models.Post;
import com.example.reddit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/posts/")
@RestController
public class PostController {


    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request) {

        return status(HttpStatus.CREATED).body(postService.savePost(request));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {

        return status(HttpStatus.OK)
                .body(postService.getPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPostById(id));
    }

    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));

    }
}
