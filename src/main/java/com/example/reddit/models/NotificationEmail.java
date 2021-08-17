package com.example.reddit.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class NotificationEmail {

    private String subject;
    private String recipient;
    private String body;

}
