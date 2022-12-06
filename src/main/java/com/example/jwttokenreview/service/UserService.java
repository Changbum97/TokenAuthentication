package com.example.jwttokenreview.service;

import com.example.jwttokenreview.domain.dto.UserJoinRequest;
import com.example.jwttokenreview.domain.entity.User;
import com.example.jwttokenreview.jwt.JwtTokenUtil;
import com.example.jwttokenreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;

    private long expireTimeMs = 1000 * 60 * 60;

    public User join(UserJoinRequest request) {

        // Username 중복 체크 로직
        userRepository.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new RuntimeException();
                });

        log.info("request Username : {}", request.getUsername());
        User savedUser = userRepository.save(request.toEntity(encoder.encode(request.getPassword())));
        return savedUser;
    }

    public String login(String username, String password) {
        // username으로 User 가져오기
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    throw new RuntimeException();
                });

        // Password Check
        if(!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }

        return JwtTokenUtil.createToken(username, secretKey, expireTimeMs);
    }
}
