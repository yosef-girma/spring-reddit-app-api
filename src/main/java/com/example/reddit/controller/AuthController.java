package com.example.reddit.controller;


import com.example.reddit.dto.RegisterRequest;
import com.example.reddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        authService.registerUser(request);
        return new ResponseEntity<>("User Registeration successful pleave verify you email",
                HttpStatus.OK);
    }

    @GetMapping("/accountverificaiton/{token}")
    public ResponseEntity<String> verifyAount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated",HttpStatus.OK);

    }
}
