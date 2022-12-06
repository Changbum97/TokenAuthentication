package com.example.jwttokenreview.service;

import com.example.jwttokenreview.domain.dto.UserJoinRequest;
import com.example.jwttokenreview.domain.entity.User;
import com.example.jwttokenreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

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
}
