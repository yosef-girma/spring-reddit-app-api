package com.example.reddit.mapper;

import com.example.reddit.dto.SubredditDto;
import com.example.reddit.models.Post;
import com.example.reddit.models.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPostList()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "postList", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}

