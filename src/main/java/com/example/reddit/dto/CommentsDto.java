package com.example.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentsDto {

    private Long id;
    private Long postId;
    private Instant createdDate;
    private String text;
    private String userName;

}
