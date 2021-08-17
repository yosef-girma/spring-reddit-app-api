package com.example.reddit.service;

import com.example.reddit.dto.RegisterRequest;
import com.example.reddit.exceptions.SpringRedditException;
import com.example.reddit.models.NotificationEmail;
import com.example.reddit.models.User;
import com.example.reddit.models.VerificationToken;
import com.example.reddit.repository.UserRepository;
import com.example.reddit.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional
    public void registerUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername().toString());
        user.setEmail(request.getEmail().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword().trim()));
        user.setCreatedDate(Instant.now());
        user.setEnabled(false);
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
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found "+username));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
