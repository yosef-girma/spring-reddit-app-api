package com.example.reddit.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UsernameNotFoundException extends  RuntimeException {

    private final String message;
}
