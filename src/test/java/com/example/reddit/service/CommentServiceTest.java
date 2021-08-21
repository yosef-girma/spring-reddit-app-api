package com.example.reddit.service;

import com.example.reddit.exceptions.SpringRedditException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CommentServiceTest {

    @Test
    @DisplayName("Test should pass when coment do not contains swear words")
    void shouldNotContainSwearWords() {

        CommentService commentService = new CommentService(
                null,null,null,
                null,null,null,null);
        // with Junit 5
       // Assertions.assertFalse(commentService.containsSwearWords("This is not swear words"));
        // with assertJ
        assertThat(commentService.containsSwearWords("This is not swear words")).isFalse();
    }
    @Test
    @DisplayName(" Should throw the exception when Exception Contains Swear Words")
    public void shouldFailWhenmmentContainSwearWords(){
        CommentService commentService = new CommentService(
                null,null,null,
                null,null,null,null);

        // with junit5
        /*SpringRedditException exception = assertThrows(SpringRedditException.class, () -> {
            commentService.containsSwearWords("This is shitty comment");
        });
        assertTrue(exception.getMessage().contains("Comments contains unacceptable language"));*/
     // throw assertj
        assertThatThrownBy(() -> {
            commentService.containsSwearWords("This is a shitty comment");
        }).isInstanceOf(SpringRedditException.class)
                .hasMessage("Comments contains unacceptable language");

    }

}