package com.example.reddit.service;

import com.example.reddit.dto.SubredditDto;
import com.example.reddit.models.Subreddit;
import com.example.reddit.repository.SubredditRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Builder
public class SubredditService {

    private final SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        Subreddit subreddit = mapToSubreddit(subredditDto);
       Subreddit savedSubreddit = subredditRepository.save(subreddit);
        subredditDto.setId(savedSubreddit.getId());
        return subredditDto;
    }
    @Transactional
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    private Subreddit mapToSubreddit(SubredditDto subredditDto) {
        return Subreddit.builder().name(subredditDto.getName())
                .description(subredditDto.getDescription()).build();

    }
    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder().name(subreddit.getName())
                .id(subreddit.getId())
                .numberOfPosts(subreddit.getPostList().size())
                .build();
    }
}
