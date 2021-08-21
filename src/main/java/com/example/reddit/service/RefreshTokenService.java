package com.example.reddit.service;

import com.example.reddit.exceptions.SpringRedditException;
import com.example.reddit.models.RefreshToken;
import com.example.reddit.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return  refreshTokenRepository.save(refreshToken);
    }
    void valiateRefreshToken(String token){
        refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new SpringRedditException("Invalid rrefresh token")
        );
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }
}

