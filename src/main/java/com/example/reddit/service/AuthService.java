package com.example.reddit.service;

import com.example.reddit.dto.AuthenticationResponse;
import com.example.reddit.dto.LoginRequest;
import com.example.reddit.dto.RegisterRequest;
import com.example.reddit.exceptions.SpringRedditException;
import com.example.reddit.models.NotificationEmail;
import com.example.reddit.models.User;
import com.example.reddit.models.VerificationToken;
import com.example.reddit.repository.UserRepository;
import com.example.reddit.repository.VerificationTokenRepository;
import com.example.reddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    @Transactional
    public void registerUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnabled(true);
        userRepository.save(user);

        String token = getVerificationToken(user);

        mailService.sendMail(new NotificationEmail("Activate your account", request.getEmail(),
                "http://localhost:8181/api/auth/accountverificaiton/" + token));

    }

    private String getVerificationToken(User user) {

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token.toString());
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Invalid token!"));
        activateUser(verificationTokenOptional.get());
    }

    @Transactional
    void activateUser(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new SpringRedditException("User not found " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse loginUser(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(token,loginRequest.getUsername());
    }
}
