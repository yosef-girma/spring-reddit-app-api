package com.example.reddit.controller;


import com.example.reddit.dto.AuthenticationResponse;
import com.example.reddit.dto.LoginRequest;
import com.example.reddit.dto.RefreshTokenRequest;
import com.example.reddit.dto.RegisterRequest;
import com.example.reddit.models.User;
import com.example.reddit.service.AuthService;
import com.example.reddit.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        authService.registerUser(request);
        return new ResponseEntity<>("User Registeration successful pleave verify you email",
                HttpStatus.OK);
    }

    @GetMapping("/accountverificaiton/{token}")
    public ResponseEntity<String> verifyAount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated", HttpStatus.OK);

    }

    @GetMapping("/getUser/{username}")
    public User getUserByUsername(@PathVariable String username){
       return  authService.getUserByUserName(username);
    }
    @PostMapping("/login")
    public AuthenticationResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
       return  authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());

    return     ResponseEntity.status(HttpStatus.OK).body("Refresh Token deleted successfully");
    }

}
