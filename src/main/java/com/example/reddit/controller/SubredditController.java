package com.example.reddit.controller;

import com.example.reddit.dto.SubredditDto;
import com.example.reddit.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subreddit")
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody @Valid SubredditDto subredditDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subredditService.save(subredditDto));

    }

    // get all subreddit
    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddit() {

        return ResponseEntity.status(HttpStatus.OK).body(
                subredditService.getAll());

    }
    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubredditById(@PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getSubreddit(id));
    }

}
